package com.wepay.wecrowd.wecrowd;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import internal.APIResponseHandler;
import internal.CampaignArrayAdapter;
import internal.LoginManager;
import models.Campaign;

public class CampaignFeedActivity extends AppCompatActivity {
    public static final String EXTRA_CAMPAIGN_ID = "com.wepay.wecrowd.CAMPAIGN_ID";

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_feed);

        listView = (ListView) findViewById(R.id.listview_campaigns);

        setUpList();
        setUpListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_campaign_feed, menu);

        return super.onCreateOptionsMenu(menu);
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

    // Utility methods
    private void setUpList() {
        final Context context = this;

        Campaign.fetchAllCampaigns(new APIResponseHandler() {
            @Override
            public void onCompletion(Campaign[] campaigns, Throwable throwable) {
                if (throwable == null) {
                    final ArrayList<Campaign> campaignList;
                    final CampaignArrayAdapter campaignArrayAdapter;
                    final Integer campaignCount;

                    // Hardcode the merchant campaigns
                    campaignCount = LoginManager.userType == LoginManager.UserType.PAYER ? campaigns.length : 2;
                    campaignList = new ArrayList<>(campaignCount);

                    for (int i = 0; i < campaignCount; ++i) {
                        campaignList.add(campaigns[i]);
                    }

                    campaignArrayAdapter = new CampaignArrayAdapter(context, campaignList);
                    listView.setAdapter(campaignArrayAdapter);
                } else {
                    // TODO: Display error
                }
            }
        });
    }

    private void setUpListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Campaign campaign = (Campaign) listView.getAdapter().getItem(position);

                beginDetailActivity(campaign.getCampaignID());
            }
        });
    }

    private void beginDetailActivity(Integer campaignID) {
        Intent intent;

        intent = new Intent(this, CampaignDetailActivity.class);
        intent.putExtra(EXTRA_CAMPAIGN_ID, campaignID);

        startActivity(intent);
    }
}
