package com.wepay.wecrowd.wecrowd;

import android.app.ListActivity;
import android.os.Bundle;

import java.util.ArrayList;

import internal.CampaignArrayAdapter;
import models.Campaign;

public class CampaignFeedActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ArrayList<Campaign> campaigns;
        final CampaignArrayAdapter campaignArrayAdapter;

        campaigns = new ArrayList<Campaign>(2);
        campaignArrayAdapter = new CampaignArrayAdapter(this, campaigns);

        campaigns.add(new Campaign("0", "Test_0"));
        campaigns.add(new Campaign("1", "Test_1"));

        setListAdapter(campaignArrayAdapter);
    }


}
