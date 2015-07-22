package models;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import internal.APIClient;
import internal.APIResponseHandler;
import internal.Constants;
import internal.JSONProcessor;

/**
 * Created by zachv on 7/21/15.
 */
public class CampaignDetail extends Campaign {
    private String description;
    private Integer progress;

    public CampaignDetail(String ID,
                          String title,
                          Integer goal,
                          String description,
                          Integer progress)
    {
        super(ID, title, goal);

        this.description = description;
        this.progress = progress;
    }

    public CampaignDetail(String ID,
                          String title,
                          Integer goal,
                          String imageURL,
                          String description,
                          Integer progress)
    {
        super(ID, title, goal, imageURL);

        this.description = description;
        this.progress = progress;
    }

    public CampaignDetail(Campaign campaign, String description, Integer progress) {
        this(campaign.campaignID,
                campaign.title,
                campaign.goal,
                campaign.imageURL,
                description,
                progress);
    }

    public static void fetchCampaignDetail(String ID, final APIResponseHandler handler) {
        String suffix = Constants.ENDPOINT_CAMPAIGNS + "/" + ID;

        APIClient.get(suffix, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Campaign campaign = campaignFromJSONObject(response);
                String description;
                Integer progress;

                description = JSONProcessor.stringFromJSON(response, Constants.CAMPAIGN_DESCRIPTION);
                progress = JSONProcessor.integerFromJSON(response, Constants.CAMPAIGN_PROGRESS);

                handler.onCompletion(new CampaignDetail(campaign, description, progress), null);
            }

            @Override
            public void onFailure(int statusCode,
                                  Header[] headers,
                                  String responseString,
                                  Throwable throwable)
            {
                handler.onCompletion((Campaign) null, throwable);
            }
        });
    }

    // Accessors
    public String getDescription() { return description; }
    public Integer getProgress() { return progress; }
}