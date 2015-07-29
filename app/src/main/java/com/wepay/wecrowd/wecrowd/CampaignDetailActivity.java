package com.wepay.wecrowd.wecrowd;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import internal.APIResponseHandler;
import internal.DonationManager;
import internal.AppNotifier;
import internal.LoginManager;
import models.Campaign;
import models.CampaignDetail;


public class CampaignDetailActivity extends AppCompatActivity {
    private CampaignDetail campaignDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_campaign_detail);

        setUpCampaignDetail();
        configureViewForUserType(LoginManager.userType);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_campaign_detail, menu);
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
    
    public void didSelectDonate(View view) {
        if (LoginManager.userType == LoginManager.UserType.PAYER) {
            startActivity(new Intent(this, ManualPaymentActivity.class));
        } else if (LoginManager.userType == LoginManager.UserType.MERCHANT) {
            // TODO: Show payment options for merchant
        }
    }

    private void setUpCampaignDetail() {
        Intent intent;
        Integer campaignID;

        intent = getIntent();
        campaignID = intent.getIntExtra(CampaignFeedActivity.EXTRA_CAMPAIGN_ID, -1);

        // Set the donation campaign
        DonationManager.configureDonationWithID(campaignID);

        CampaignDetail.fetchCampaignDetail(campaignID, new APIResponseHandler() {
            @Override
            public void onCompletion(Campaign campaign, Throwable throwable) {
                if (throwable == null) {
                    campaignDetail = (CampaignDetail) campaign;
                    configureViewForCampaignDetail((CampaignDetail) campaign);
                } else {
                    AppNotifier.showSimpleError(CampaignDetailActivity.this,
                            getString(R.string.error_fetch_title),
                            getString(R.string.error_campaign_detail_fetch_preface),
                            throwable.getLocalizedMessage());
                }
            }
        });
    }

    private void configureViewForCampaignDetail(CampaignDetail campaignDetail) {
        Integer progressPercent;
        TextView titleTextView, progressTextView;
        ProgressBar progressBar;
        final ImageView imageView;

        progressPercent = floatProgress(campaignDetail.getProgress(), campaignDetail.getGoal());

        imageView = (ImageView) findViewById(R.id.campaign_detail_image);
        titleTextView = (TextView) findViewById(R.id.campaign_detail_title);
        progressTextView = (TextView) findViewById(R.id.campaign_detail_progress_text);
        progressBar = (ProgressBar) findViewById(R.id.campaign_detail_progress_bar);

        progressTextView.setText(stringFromProgress(progressPercent));
        progressBar.setProgress(progressPercent);

        titleTextView.setText(campaignDetail.getTitle());

        campaignDetail.fetchImage(campaignDetail, new APIResponseHandler() {
            @Override
            public void onCompletion(Bitmap bitmap, Throwable throwable) {
                if (throwable == null) {
                    imageView.setImageBitmap(bitmap);
                    imageView.invalidate();
                } else {
                    AppNotifier.showSimpleError(CampaignDetailActivity.this,
                            getString(R.string.error_fetch_title),
                            getString(R.string.error_campaign_image_fetch_preface),
                            throwable.getLocalizedMessage());
                }
            }
        });
    }

    private void configureViewForUserType(LoginManager.UserType userType) {
        Button donateButton = (Button) findViewById(R.id.campaign_detail_button_donate);

        if (userType == LoginManager.UserType.PAYER) {
            donateButton.setText(getText(R.string.title_button_donate));
        } else if (userType == LoginManager.UserType.MERCHANT) {
            donateButton.setText(getText(R.string.title_button_accept_donation));
        }
    }

    private String stringFromProgress(Integer progress) {
        return progress.toString() + "%" + " funded";
    }

    private Integer floatProgress(Integer current, Integer goal) {
        Float progress = current.floatValue() / goal.floatValue();

        return Math.round(progress * 100);
    }
}
