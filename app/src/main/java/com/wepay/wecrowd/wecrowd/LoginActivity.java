package com.wepay.wecrowd.wecrowd;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import internal.APIResponseHandler;
import internal.LoginManager;
import models.User;

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
        String textCredentials, textPassword;

        textCredentials = entryCredentials.getText().toString();
        textPassword = entryPassword.getText().toString();

        LoginManager.login(textCredentials, textPassword, new APIResponseHandler() {
            @Override
            public void onCompletion(User user, Throwable throwable) {
                super.onCompletion(user, throwable);

                if (throwable == null) {
                    beginNextActivity();
                } else {
                    showLoginErrorWithMessage(throwable.getLocalizedMessage());
                }
            }
        });
    }

    private void beginNextActivity() {
        startActivity(new Intent(this, CampaignFeedActivity.class));
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
