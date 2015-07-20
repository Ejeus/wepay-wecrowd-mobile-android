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

        entryCredentials.setText("wp.android.example@wepay.com", TextView.BufferType.EDITABLE);
        entryPassword.setText("password", TextView.BufferType.EDITABLE);
    }

    public void didRequestLogin(View view) {
        RequestParams params;
        String textCredentials, textPassword;

        textCredentials = entryCredentials.getText().toString();
        textPassword = entryPassword.getText().toString();

        params = new RequestParams();
        params.put("user_email", textCredentials);
        params.put("password", textPassword);

        APIClient.post("login", params, new JsonHttpResponseHandler() {
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

                AlertDialog.Builder errorBuilder;
                AlertDialog dialog;

                errorBuilder = new AlertDialog.Builder(LoginActivity.this);
                errorBuilder.setTitle("Unable to login.")
                            .setMessage("Login failed with error: ")
                            .setNegativeButton("Close", null);

                dialog = errorBuilder.create();
                dialog.show();
            }
        });
    }

    private void beginNextActivity() {
        Intent intent;

        intent = new Intent(this, CampaignFeedActivity.class);

        startActivity(intent);
    }
}
