package com.wepay.wecrowd.wecrowd;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import internal.APIResponseHandler;
import models.Campaign;
import models.CampaignDetail;


public class CampaignDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpCampaignDetail();

        setContentView(R.layout.activity_campaign_detail);
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

    private void setUpCampaignDetail() {
        Intent intent;
        String campaignID;

        intent = getIntent();
        campaignID = intent.getStringExtra(CampaignFeedActivity.EXTRA_CAMPAIGN_ID);

        CampaignDetail.fetchCampaignDetail(campaignID, new APIResponseHandler() {
            @Override
            public void onCompletion(Campaign campaign, Throwable throwable) {
                if (throwable == null) {
                    configureViewForCampaignDetail((CampaignDetail) campaign);
                } else {
                    // TODO: handle error
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
                    // TODO: handle error
                }
            }
        });
    }

    private String stringFromProgress(Integer progress) {
        return progress.toString() + "%" + " funded";
    }

    private Integer floatProgress(Integer current, Integer goal) {
        Float progress = current.floatValue() / goal.floatValue();

        return Math.round(progress * 100);
    }
}
