package com.wepay.wecrowd.wecrowd;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import models.Campaign;

public class CampaignFeedActivity extends ListActivity implements Callback {
    public static final String EXTRA_CAMPAIGN_ID = "com.wepay.wecrowd.CAMPAIGN_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpList();
        setUpListener();
    }

    @Override
    public void onCompletion(Object object) {
        ListView list = getListView();

        int start = list.getFirstVisiblePosition();

        for (int i = start, j = list.getLastVisiblePosition(); i <= j; ++i) {
            if (object == list.getItemAtPosition(i)) {
                View view = list.getChildAt(i-start);

                list.getAdapter().getView(i, view, list);
                break;
            }
        }
    }

    // Utility methods
    private void setUpList() {
        final Context context = this;
        Campaign.callback = this;

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
