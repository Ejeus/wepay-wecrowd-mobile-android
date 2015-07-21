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

    // Constructors
    public Campaign(String ID, String title) {
        this.campaignID = ID;
        this.title = title;
    }

    public Campaign(String ID, String title, String imageURL) {
        this(ID, title);
        this.imageURL = imageURL;
    }

    // Utilities
    // TODO: Make custom response handler
    public void fetchImage(final AsyncHttpResponseHandler responseHandler) {
        APIClient.getFromRaw(this.imageURL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                imageBMP = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                responseHandler.onSuccess(i, headers, bytes);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.e(getClass().getName(), "Unable to fetch the image. " + throwable.getLocalizedMessage(), throwable);

                responseHandler.onFailure(i, headers, bytes, throwable);
            }
        });
    }

    // Accessors
    public String getCampaignID() { return campaignID; }
    public String getTitle() { return title; }
    public Bitmap getImageBMP() { return imageBMP; }
}
