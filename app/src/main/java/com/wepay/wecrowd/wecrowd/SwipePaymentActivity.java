package com.wepay.wecrowd.wecrowd;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


public class SwipePaymentActivity extends ActionBarActivity {

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
}
