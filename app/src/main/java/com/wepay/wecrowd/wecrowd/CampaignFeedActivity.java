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
import internal.AppNotifier;
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
                    AppNotifier.showSimpleError(context,
                            getString(R.string.error_fetch_title),
                            getString(R.string.error_campaigns_fetch_preface),
                            throwable.getLocalizedMessage());
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
