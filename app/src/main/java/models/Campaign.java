package models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import org.apache.http.Header;
import internal.APIClient;
import internal.APIResponseHandler;

/**
 * Created by zachv on 7/20/15.
 */

public class Campaign {
    private String campaignID;
    private String title;
    private String imageURL;
    private String endDate;
    private Bitmap imageBMP;
    private Integer goal;

    // Constructors
    public Campaign(String ID, String title) {
        this.campaignID = ID;
        this.title = title;
    }

    public Campaign(String ID, String title, Integer goal) {
        this(ID, title);
        this.goal = goal;
    }

    public Campaign(String ID, String title, Integer goal, String imageURL) {
        this(ID, title, goal);
        this.imageURL = imageURL;
    }

    // Utilities
    public void fetchImage(final APIResponseHandler responseHandler) {
        final Campaign campaign = this;

        APIClient.getFromRaw(this.imageURL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                imageBMP = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                responseHandler.onCompletion(campaign, null);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.e(getClass().getName(), "Unable to fetch the image. " + throwable.getLocalizedMessage(), throwable);

                responseHandler.onCompletion(campaign, throwable);
            }
        });
    }

    // Accessors
    public String getCampaignID() { return campaignID; }
    public String getTitle() { return title; }
    public String getEndDate() { return endDate; }
    public Bitmap getImageBMP() { return imageBMP; }
    public Integer getGoal() { return goal; }
}
