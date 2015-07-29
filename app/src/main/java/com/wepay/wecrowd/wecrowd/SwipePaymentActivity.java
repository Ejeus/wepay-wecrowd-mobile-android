package com.wepay.wecrowd.wecrowd;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.wepay.android.SwiperHandler;
import com.wepay.android.TokenizationHandler;
import com.wepay.android.enums.SwiperStatus;
import com.wepay.android.models.*;
import com.wepay.android.models.Error;


public class SwipePaymentActivity extends AppCompatActivity implements SwiperHandler, TokenizationHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_payment);

        setUpViewInformation();
    }


    private void setUpViewInformation() {
        ViewGroup donationField;
        TextView donateTag;
        EditText donateEditText;

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

    }

    @Override
    public void onStatusChange(SwiperStatus swiperStatus) {

    }

    @Override
    public void onSuccess(PaymentInfo paymentInfo, PaymentToken paymentToken) {

    }

    @Override
    public void onError(PaymentInfo paymentInfo, Error error) {

    }
}
