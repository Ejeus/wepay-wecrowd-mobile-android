package com.wepay.wecrowd.wecrowd;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.wepay.android.SignatureHandler;
import com.wepay.android.models.*;

import internal.AppNotifier;
import internal.DonationManager;
import internal.PaymentManager;


public class SignatureActivity extends AppCompatActivity implements SignatureHandler {
    SignaturePad signaturePad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);

        signaturePad = (SignaturePad) findViewById(R.id.signature_pad);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signature, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_clear) {
            signaturePad.clear();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void didSubmitSignature(View view) {
        PaymentManager.storeSignatureImage(this,
                signaturePad.getSignatureBitmap(),
                DonationManager.getCheckoutID(),
                this);
    }

    @Override
    public void onSuccess(String signatureURL, String checkoutID) {
        Log.i(getClass().getName(), "Signature URL:" + signatureURL);
    }

    @Override
    public void onError(Bitmap bitmap, String checkoutID, com.wepay.android.models.Error error) {
        AppNotifier.showSimpleError(this,
                "Unable to store signature",
                "Failed to store signature image",
                error.getLocalizedMessage());
    }
}
