package com.wepay.wecrowd.wecrowd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

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
        final CampaignDetail campaignDetail;

        intent = getIntent();
//        campaignID = intent.getStringExtra(CampaignFeedActivity.EXTRA_CAMPAIGN_ID);
        campaignID = "28";

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

    }
}
