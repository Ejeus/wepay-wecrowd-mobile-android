package com.wepay.wecrowd.wecrowd;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Objects;

import internal.APIResponseHandler;
import internal.Callback;
import internal.CampaignArrayAdapter;
import models.Campaign;

public class CampaignFeedActivity extends ListActivity implements Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
}
