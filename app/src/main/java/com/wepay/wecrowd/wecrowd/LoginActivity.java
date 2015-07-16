package com.wepay.wecrowd.wecrowd;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class LoginActivity extends AppCompatActivity {
    public final static String EXTRA_CREDENTIALS = "com.wepay.wecrowd.CREDENTIALS";
    public final static String EXTRA_PASSWORD = "com.wepay.wecrowd.PASSWORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    public void didRequestLogin(View view) {
        Intent intent;
        EditText entryCredentials, entryPassword;
        String textCredentials, textPassword;

        intent = new Intent(this, CampaignFeedActivity.class);

        entryCredentials = (EditText) findViewById(R.id.edit_text_credentials);
        entryPassword = (EditText) findViewById(R.id.edit_text_credentials);

        textCredentials = entryCredentials.getText().toString();
        textPassword = entryPassword.getText().toString();

        intent.putExtra(EXTRA_CREDENTIALS, textCredentials);
        intent.putExtra(EXTRA_PASSWORD, textPassword);

        startActivity(intent);
    }
}
