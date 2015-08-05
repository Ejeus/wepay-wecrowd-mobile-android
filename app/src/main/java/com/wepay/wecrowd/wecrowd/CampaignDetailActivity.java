package com.wepay.wecrowd.wecrowd;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
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

    public void didSelectDonate(View view) {
        final Context context = this;

        if (LoginManager.userType == LoginManager.UserType.PAYER) {
            startActivity(new Intent(this, ManualPaymentActivity.class));
        } else if (LoginManager.userType == LoginManager.UserType.MERCHANT) {
            PopupMenu menu = new PopupMenu(this, findViewById(R.id.campaign_detail_button_donate));

            menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_donation_manual_payment: {
                            startActivity(new Intent(context, ManualPaymentActivity.class));
                            return true;
                        }
                        case R.id.menu_donation_swipe_payment: {
                            startActivity(new Intent(context, SwipePaymentActivity.class));
                            return true;
                        }
                        default: { return false; }
                    }
                }
            });

            menu.inflate(R.menu.menu_donation);
            menu.show();
        }
    }

    private void setUpCampaignDetail() {
        Intent intent;
        Integer campaignID;

        intent = getIntent();
//        campaignID = intent.getIntExtra(CampaignFeedActivity.EXTRA_CAMPAIGN_ID, -1);
        campaignID = 28;

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
        TextView progressTextView;
        ProgressBar progressBar;
        final ImageView imageView;

        setTitle(campaignDetail.getTitle());

        progressPercent = floatProgress(campaignDetail.getProgress(), campaignDetail.getGoal());

        imageView = (ImageView) findViewById(R.id.campaign_detail_image);
        progressTextView = (TextView) findViewById(R.id.campaign_detail_progress_text);
        progressBar = (ProgressBar) findViewById(R.id.campaign_detail_progress_bar);

        progressTextView.setText(stringFromProgress(progressPercent));
        progressBar.setProgress(progressPercent);

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
