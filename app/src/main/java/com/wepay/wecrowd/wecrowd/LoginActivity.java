package com.wepay.wecrowd.wecrowd;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import internal.APIClient;

public class LoginActivity extends AppCompatActivity {
    EditText entryCredentials, entryPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        entryCredentials = (EditText) findViewById(R.id.edit_text_credentials);
        entryPassword = (EditText) findViewById(R.id.edit_text_password);

        entryCredentials.setText(R.string.demo_email, TextView.BufferType.EDITABLE);
        entryPassword.setText(R.string.demo_password, TextView.BufferType.EDITABLE);
    }

    public void didRequestLogin(View view) {
        RequestParams params;
        String textCredentials, textPassword;

        textCredentials = entryCredentials.getText().toString();
        textPassword = entryPassword.getText().toString();

        params = new RequestParams();
        params.put(getString(R.string.api_email_key), textCredentials);
        params.put(getString(R.string.api_password_key), textPassword);

        APIClient.post(getString(R.string.api_login_endpoint), params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                beginNextActivity();
            }

            @Override
            public void onFailure(int statusCode,
                                  Header[] headers,
                                  String responseString,
                                  Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);

                showLoginErrorWithMessage(responseString);
            }
        });
    }

    private void beginNextActivity() {
        Intent intent;

        intent = new Intent(this, CampaignFeedActivity.class);

        startActivity(intent);
    }

    private void showLoginErrorWithMessage(String message) {
        AlertDialog.Builder errorBuilder;
        AlertDialog dialog;

        errorBuilder = new AlertDialog.Builder(LoginActivity.this);
        errorBuilder.setTitle(R.string.error_login_title)
                .setMessage(getString(R.string.error_login_message) + ". Error: " + message)
                .setNegativeButton(R.string.dialog_button_close, null);

        dialog = errorBuilder.create();
        dialog.show();
    }
}
