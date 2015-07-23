package com.wepay.wecrowd.wecrowd;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import internal.APIResponseHandler;
import internal.Callback;
import internal.CampaignArrayAdapter;
import internal.LoginManager;
import models.Campaign;

public class CampaignFeedActivity extends ListActivity {
    public static final String EXTRA_CAMPAIGN_ID = "com.wepay.wecrowd.CAMPAIGN_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                    campaignList = new ArrayList<Campaign>(campaignCount);

                    for (int i = 0; i < campaignCount; ++i) {
                        campaignList.add(campaigns[i]);
                    }

                    campaignArrayAdapter = new CampaignArrayAdapter(context, campaignList);
                    setListAdapter(campaignArrayAdapter);
                } else {
                    // TODO: Display error
                }
            }
        });
    }

    private void setUpListener() {
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Campaign campaign = (Campaign) getListAdapter().getItem(position);

                beginDetailActivity(campaign.getCampaignID());
            }
        });
    }

    private void beginDetailActivity(String campaignID) {
        Intent intent;

        intent = new Intent(this, CampaignDetailActivity.class);
        intent.putExtra(EXTRA_CAMPAIGN_ID, campaignID);

        startActivity(intent);
    }
}
