package com.wepay.wecrowd.wecrowd;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;

import internal.APIResponseHandler;
import internal.CampaignArrayAdapter;
import models.Campaign;

public class CampaignFeedActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Context context = this;

        Campaign.fetchAllCampaigns(new APIResponseHandler() {
            @Override
            public void onCompletion(Campaign[] campaigns, Throwable throwable) {
                final ArrayList<Campaign> campaignList;
                final CampaignArrayAdapter campaignArrayAdapter;

                campaignList = new ArrayList<Campaign>(campaigns.length);

                for (int i = 0; i < campaigns.length; ++i) { campaignList.add(campaigns[i]); }

                campaignArrayAdapter = new CampaignArrayAdapter(context, campaignList);
                setListAdapter(campaignArrayAdapter);
            }
        });
    }


}
