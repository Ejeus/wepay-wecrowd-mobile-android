package com.wepay.wecrowd.wecrowd;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.wepay.android.SwiperHandler;
import com.wepay.android.TokenizationHandler;
import com.wepay.android.enums.SwiperStatus;
import com.wepay.android.models.*;
import com.wepay.android.models.Error;

import internal.APIResponseHandler;
import internal.AppNotifier;
import internal.DonationManager;
import internal.PaymentManager;


public class SwipePaymentActivity extends AppCompatActivity
        implements SwiperHandler, TokenizationHandler
{
    TextView statusTextView;
    EditText donateEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_payment);

        storeLayoutViews();
        setUpViewInformation();
    }

    private void storeLayoutViews() {
        statusTextView = (TextView) findViewById(R.id.swipe_payment_status);
    }

    private void setUpViewInformation() {
        ViewGroup donationField;
        TextView donateTag;

        donationField = (ViewGroup) findViewById(R.id.swiper_payment_donation_field);
        donateTag = (TextView) donationField.getChildAt(0);
        donateEditText = (EditText) donationField.getChildAt(1);

        donateTag.setText(getString(R.string.title_donation_manual));
        donateEditText.setText(getString(R.string.demo_payer_donation_amount));
    }

    @Override
    public void onSuccess(PaymentInfo paymentInfo) {

    }

    @Override
    public void onError(com.wepay.android.models.Error error) {
        AppNotifier.showSimpleError(this, "Unable to complete card swipe",
                "The card swipe failed", error.getLocalizedMessage());
    }

    @Override
    public void onStatusChange(SwiperStatus status) {
        String statusMessage = getString(R.string.title_status_swipe);

        if (status.equals(SwiperStatus.NOT_CONNECTED)) {
            statusMessage = getString(R.string.message_swiper_not_connected);
        } else if (status.equals(SwiperStatus.WAITING_FOR_SWIPE)) {
            statusMessage = getString(R.string.message_swiper_waiting);
        } else if (status.equals(SwiperStatus.SWIPE_DETECTED)) {
            statusMessage = getString(R.string.message_swiper_detected);
        } else if (status.equals(SwiperStatus.TOKENIZING)) {
            statusMessage = getString(R.string.message_swiper_tokenizing);
        } else if (status.equals(SwiperStatus.STOPPED)) {
            statusMessage = getString(R.string.message_swiper_stopped);
        }

        statusTextView.setText(statusMessage);
    }

    @Override
    public void onSuccess(PaymentInfo paymentInfo, PaymentToken paymentToken) {
        final Context context = this;

        DonationManager.configureDonationWithToken(paymentToken.getTokenId());

        DonationManager.makeDonation(this, new APIResponseHandler() {
            @Override
            public void onCompletion(String value, Throwable throwable) {
                AppNotifier.dismissIndeterminateProgress();

                if (throwable == null) {
                    AppNotifier.showSimpleSuccess(context,
                            getString(R.string.message_success_donation));

                    startActivity(new Intent(context, SignatureActivity.class));
                } else {
                    AppNotifier.showSimpleError(context,
                            getString(R.string.message_failure_donation),
                            getString(R.string.error_donation_preface),
                            throwable.getLocalizedMessage());
                }
            }
        });

        AppNotifier.showIndeterminateProgress(context, getString(R.string.message_processing));
    }

    @Override
    public void onError(PaymentInfo paymentInfo, Error error) {
        AppNotifier.dismissIndeterminateProgress();

        AppNotifier.showSimpleError(this, getString(R.string.message_failure_tokenization),
                getString(R.string.error_tokenization_preface),
                error.getLocalizedMessage());

        statusTextView.setText(getString(R.string.title_status_swipe));
    }

    public void didChooseDonate(View view) {
        DonationManager.configureDonationWithAmount(Integer.parseInt(donateEditText.getText().toString()));

        statusTextView.setText("Starting card reader...");
        PaymentManager.startCardSwipeTokenization(this, this, this);
    }
}
