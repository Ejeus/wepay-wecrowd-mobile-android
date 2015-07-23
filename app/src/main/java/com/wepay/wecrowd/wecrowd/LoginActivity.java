package com.wepay.wecrowd.wecrowd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import internal.APIResponseHandler;
import internal.ErrorNotifier;
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
                    ErrorNotifier.showSimpleError(LoginActivity.this,
                            getString(R.string.error_login_title),
                            getString(R.string.error_login_preface),
                            throwable.getLocalizedMessage());
                }
            }
        });
    }

    private void beginNextActivity() {
        startActivity(new Intent(this, CampaignFeedActivity.class));
    }
}
