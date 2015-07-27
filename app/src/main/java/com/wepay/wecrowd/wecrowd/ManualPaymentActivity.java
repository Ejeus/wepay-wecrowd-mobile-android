package com.wepay.wecrowd.wecrowd;

import android.app.Application;
import android.location.Address;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.wepay.android.TokenizationHandler;
import com.wepay.android.WePay;
import com.wepay.android.enums.PaymentMethod;
import com.wepay.android.models.*;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import internal.LoginManager;
import internal.PaymentManager;

public class ManualPaymentActivity extends AppCompatActivity implements TokenizationHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_payment);

        setUpInformationFields();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_manual_payment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void didChooseDonate(View view) {
        // String firstName, String lastName, String email, String paymentDescription,
        // Address billingAddress, Address shippingAddress, PaymentMethod paymentMethod,
        // String ccNumber, String cvv, String expMonth, String expYear, boolean virtualTerminal

        Address address;
        Boolean virtualTerminal;

        address = new Address(Locale.getDefault());
        address.setAddressLine(0, "350 Convention Way");
        address.setLocality("Redwood City");
        address.setPostalCode("94063");
        address.setCountryCode("US");


        virtualTerminal = LoginManager.userType == LoginManager.UserType.MERCHANT;

        PaymentInfo paymentInfo = new PaymentInfo(getValueForId(R.id.manual_payment_first_name),
                getValueForId(R.id.manual_payment_last_name),
                getValueForId(R.id.manual_payment_email),
                "WeCrowd Android Demo Donation",
                address, address, PaymentMethod.MANUAL,
                getValueForId(R.id.manual_payment_card_number),
                getValueForId(R.id.manual_payment_cvv),
                getValueForId(R.id.manual_payment_expiration), "2020", virtualTerminal);

        PaymentManager.tokenizeInfo(this, paymentInfo, this);
    }

    private void setUpInformationFields() {
        final ViewGroup fieldViewGroup;
        TextView tagTextView;
        EditText entryEditText;
        final List<Map.Entry<String, String>> fields;

        // Grab the 'root' group containing all the fields
        fieldViewGroup = (ViewGroup) findViewById(R.id.manual_payment_fields); //getFieldRootView();
        fields = fieldConfigurationList();

        for (int i = 0; i < fieldViewGroup.getChildCount(); ++i) {
            // Grab the row containing the tag and entry views
            ViewGroup field = (ViewGroup) fieldViewGroup.getChildAt(i);

            // Grab the views in the field
            tagTextView = (TextView) field.findViewById(R.id.linear_tagged_title);
            entryEditText = (EditText) field.findViewById(R.id.linear_tagged_entry);

            // Set the text for the field child views
            tagTextView.setText(fields.get(i).getKey());
            entryEditText.setText(fields.get(i).getValue(), TextView.BufferType.EDITABLE);
        }
    }

    private List<Map.Entry<String, String>> fieldConfigurationList() {
        List<Map.Entry<String, String>> configList = new ArrayList<>();

        configList.add(fieldConfiguration(getString(R.string.title_donation), getString(R.string.demo_payer_donation_amount)));
        configList.add(fieldConfiguration(getString(R.string.title_first_name), getString(R.string.demo_payer_first_name)));
        configList.add(fieldConfiguration(getString(R.string.title_last_name), getString(R.string.demo_payer_last_name)));
        configList.add(fieldConfiguration(getString(R.string.title_email), getString(R.string.demo_payer_email)));
        configList.add(fieldConfiguration(getString(R.string.title_card_number), getString(R.string.demo_payer_card_number)));
        configList.add(fieldConfiguration(getString(R.string.title_expiration), getString(R.string.demo_payer_expiration_month)));
        configList.add(fieldConfiguration(getString(R.string.title_cvv), getString(R.string.demo_payer_cvv)));
        configList.add(fieldConfiguration(getString(R.string.title_zip_code), getString(R.string.demo_payer_expiration_zip_code)));

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
        return (ViewGroup) getFieldRootView().findViewById(ID);
    }

    @Override
    public void onSuccess(PaymentInfo paymentInfo, PaymentToken paymentToken) {
        Log.i(getClass().getName(), "Tokenization successful!");
    }

    @Override
    public void onError(PaymentInfo paymentInfo, com.wepay.android.models.Error error) {

    }
}
