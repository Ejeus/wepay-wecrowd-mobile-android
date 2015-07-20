package models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import org.apache.http.Header;
import internal.APIClient;

/**
 * Created by zachv on 7/20/15.
 */

public class Campaign {
    private String campaignID;
    private String title;
    private String imageURL;
    private Bitmap imageBMP;

    // Factory methods
    public static Campaign create(String ID, String title) {
        return new Campaign(ID, title);
    }

    public static Campaign create(String ID, String title, String imageURL) {
        Campaign campaign = new Campaign(ID, title, imageURL);
        campaign.fetchImage();

        return campaign;
    }

    // Constructors
    private Campaign(String ID, String title) {
        init(ID, title);
    }

    private Campaign(String ID, String title, String imageURL) {
        init(ID, title, imageURL);
    }

    // Initialization helpers
    private void init(String ID, String title) {
        this.campaignID = ID;
        this.title = title;
    }

    private void init(String ID, String title, String imageURL) {
        init(ID, title);
        this.imageURL = imageURL;
    }

    // Utilities
    private void fetchImage() {
        APIClient.get(this.imageURL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                imageBMP = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.e(getClass().getName(), "Unable to load the image", throwable);
            }
        });
    }

    // Accessors
    public String getCampaignID() { return campaignID; }
    public String getTitle() { return title; }
    public String getImageURL() { return imageURL; }
}
