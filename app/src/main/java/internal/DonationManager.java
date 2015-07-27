package internal;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by zachv on 7/27/15.
 */
public class DonationManager {
    private static String campaignID;

    public static void makeDonation(String amount,
                                    String donatorName,
                                    String donatorEmail,
                                    String creditCardID,
                                    final APIResponseHandler responseHandler)
    {
        RequestParams params;

        params = new RequestParams();
        params.put(Constants.CAMPAIGN_ID, campaignID);
        params.put(Constants.PARAM_USER_NAME, donatorName);
        params.put(Constants.LOGIN_EMAIL, donatorEmail);
        params.put(Constants.PARAM_CREDIT_CARD_ID, creditCardID);

        APIClient.post(Constants.ENDPOINT_DONATE, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.i("TEST", "Donation succeeded");
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.i("TEST", "Donation failed");
                }
            }
        );
    }

    public static void setCampaignID(String ID) { campaignID = ID; }
}
