package internal;

import android.content.Context;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import models.Donation;

/**
 * Created by zachv on 7/27/15.
 */
public class DonationManager {
    private static Donation donation;

    public static void configureDonationWithID(Integer ID) {
        if (donation == null) { donation = new Donation(); }

        donation.setCampaignID(ID);
    }

    public static void configureDonationWithToken(String creditCardID) {
        if (donation == null) { donation = new Donation(); }

        donation.setCreditCardID(creditCardID);
    }

    public static void configureDonationWithAmount(Integer amount) {
        if (donation == null) { donation = new Donation(); }

        donation.setAmount(amount);
    }

    public static void makeDonation(Context context, final APIResponseHandler responseHandler) {
        Map<String, Object> paramMap;

        // TODO: Add assert for donation allocation
        paramMap = new HashMap<>();
        paramMap.put(Constants.CAMPAIGN_ID, donation.getCampaignID());
        paramMap.put(Constants.PARAM_CREDIT_CARD_ID, donation.getCreditCardID());
        paramMap.put(Constants.PARAM_DONATION_AMOUNT, donation.getAmount());

        APIClient.post(context, Constants.ENDPOINT_DONATE, paramMap, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    String value;

                    value = JSONProcessor.stringFromJSON(response, Constants.PARAM_CHECKOUT_ID);

                    responseHandler.onCompletion(value, null);
                }

                @Override
                public void onFailure(int statusCode,
                                      Header[] headers,
                                      String responseString,
                                      Throwable throwable)
                {
                    responseHandler.onCompletion((String) null, throwable);
                }
            }
        );
    }
}
