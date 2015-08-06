package com.wepay.wecrowd.wecrowd;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.wepay.android.TokenizationHandler;
import com.wepay.android.enums.PaymentMethod;
import com.wepay.android.models.*;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import internal.APIResponseHandler;
import internal.DonationManager;
import internal.AppNotifier;
import internal.InputManager;
import internal.LoginManager;
import internal.PaymentManager;

public class ManualPaymentActivity extends AppCompatActivity implements TokenizationHandler {
    List<Map.Entry<String, String>> fields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_payment);

        setUpInformationFields();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        setUpInformationFields();
    }

    // Button message
    public void didChooseDonate(View view) {
        performDonation();
    }

    private void performDonation() {
        Address address;
        Boolean virtualTerminal;
        EditText expirationMonthEditText, expirationYearEditText;
        String expirationMonth, expirationYear;

        address = new Address(Locale.getDefault());
        address.setAddressLine(0, getString(R.string.demo_address_line));
        address.setLocality(getString(R.string.demo_address_locality));
        address.setPostalCode(getString(R.string.demo_address_postal_code));
        address.setCountryCode(getString(R.string.demo_address_country_code));

        virtualTerminal = LoginManager.userType == LoginManager.UserType.MERCHANT;

        expirationMonthEditText = (EditText) findViewById(R.id.manual_payment_month_entry);
        expirationYearEditText = (EditText) findViewById(R.id.manual_payment_year_entry);

        expirationMonth = expirationMonthEditText.getText().toString();
        expirationYear = expirationYearEditText.getText().toString();

        PaymentInfo paymentInfo = new PaymentInfo(getValueForId(R.id.manual_payment_first_name),
                getValueForId(R.id.manual_payment_last_name),
                getValueForId(R.id.manual_payment_email),
                getString(R.string.demo_donation_description),
                address, address, PaymentMethod.MANUAL,
                getValueForId(R.id.manual_payment_card_number),
                getValueForId(R.id.manual_payment_cvv),
                expirationMonth, expirationYear, virtualTerminal);

        PaymentManager.tokenizeInfo(this, paymentInfo, this);

        // Show the loading view
        AppNotifier.showIndeterminateProgress(this, getString(R.string.message_processing));
    }

    private void setUpInformationFields() {
        final ViewGroup fieldViewGroup;
        TextView tagTextView;
        EditText entryEditText, expirationMonthEditText, expirationYearEditText;

        // Grab the group containing all the fields
        fieldViewGroup = (ViewGroup) findViewById(R.id.manual_payment_fields);
        if (fields == null) { fields = fieldConfigurationList(); }

        for (int i = 0; i < fieldViewGroup.getChildCount(); ++i) {
            // Grab the row containing the tag and entry views
            ViewGroup field = (ViewGroup) fieldViewGroup.getChildAt(i);

            // Grab the views in the field
            tagTextView = (TextView) field.findViewById(R.id.linear_tagged_title);
            entryEditText = (EditText) field.findViewById(R.id.linear_tagged_entry);
            InputManager.setKeyboardDismissForEditText(this, entryEditText);

            // Set the text for the field child views
            tagTextView.setText(fields.get(i).getKey());
            entryEditText.setText(fields.get(i).getValue(), TextView.BufferType.EDITABLE);
        }

        expirationMonthEditText = (EditText) findViewById(R.id.manual_payment_month_entry);
        expirationYearEditText = (EditText) findViewById(R.id.manual_payment_year_entry);

        InputManager.setKeyboardDismissForEditText(this, expirationMonthEditText);
        InputManager.setKeyboardDismissForEditText(this, expirationYearEditText);
    }

    // Couldn't figure out how to just add the strings directly to XML while reusing the ViewGroup
    // with the <include> tag, so using a programmatic structure.
    private List<Map.Entry<String, String>> fieldConfigurationList() {
        List<Map.Entry<String, String>> configList = new ArrayList<>();

        configList.add(fieldConfiguration(getString(R.string.title_donation), getAmount()));
        configList.add(fieldConfiguration(getString(R.string.title_first_name), getFirstName()));
        configList.add(fieldConfiguration(getString(R.string.title_last_name), getLastName()));
        configList.add(fieldConfiguration(getString(R.string.title_email), getEmail()));
        configList.add(fieldConfiguration(getString(R.string.title_card_number), getCardNumber()));
        configList.add(fieldConfiguration(getString(R.string.title_cvv), getCVV()));
        configList.add(fieldConfiguration(getString(R.string.title_zip_code), getZipCode()));

        return configList;
    }

    private Map.Entry<String, String> fieldConfiguration(String tag, String value) {
        return new AbstractMap.SimpleImmutableEntry<>(tag, value);
    }

    private String getValueForId(int ID) {
        TextView textView = (TextView) getFieldForID(ID).findViewById(R.id.linear_tagged_entry);

        return textView.getText().toString();
    }

    private ViewGroup getFieldForID(int ID) {
        return (ViewGroup) findViewById(R.id.manual_payment_fields).findViewById(ID);
    }



    @Override
    public void onSuccess(PaymentInfo paymentInfo, PaymentToken paymentToken) {
        final Context context = this;
        final Integer amount = Integer.parseInt(getValueForId(R.id.manual_payment_donation));

        DonationManager.configureDonationWithToken(paymentToken.getTokenId());
        DonationManager.configureDonationWithAmount(amount);

        DonationManager.makeDonation(this, new APIResponseHandler() {
            @Override
            public void onCompletion(String value, Throwable throwable) {
                AppNotifier.dismissIndeterminateProgress();

                if (throwable == null) {
                    AppNotifier.showSimpleSuccess(context,
                            getString(R.string.message_success_donation));

                    finish();
                } else {
                    AppNotifier.showSimpleError(context,
                            getString(R.string.message_failure_donation),
                            getString(R.string.error_donation_preface),
                            throwable.getLocalizedMessage());
                }
            }
        });
    }

    @Override
    public void onError(PaymentInfo paymentInfo, com.wepay.android.models.Error error) {
        AppNotifier.dismissIndeterminateProgress();

        AppNotifier.showSimpleError(this, getString(R.string.message_failure_tokenization),
                getString(R.string.error_tokenization_preface),
                error.getLocalizedMessage());
    }

    // Default field value getters
    private String getAmount() { return getString(R.string.demo_payer_donation_amount); }
    private String getFirstName() { return getString(R.string.demo_payer_first_name); }
    private String getLastName() { return getString(R.string.demo_payer_last_name); }
    private String getEmail() { return getString(R.string.demo_payer_email); }
    private String getCardNumber() { return getString(R.string.demo_payer_card_number); }
    private String getCVV() { return getString(R.string.demo_payer_cvv); }
    private String getZipCode() { return getString(R.string.demo_payer_expiration_zip_code); }
}
