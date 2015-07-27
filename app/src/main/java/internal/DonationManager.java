package internal;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import models.Donation;

/**
 * Created by zachv on 7/27/15.
 */
public class DonationManager {
    private static Donation donation;

    public static void configureDonationWithID(Integer ID) {
        if (donation == null) { donation = new Donation(); }

        donation.setDonationInfo(ID);
    }

    public static void configureDonationForName(String creditCardID, Integer amount) {
        if (donation == null) { donation = new Donation(); }

        donation.setDonationInfo(creditCardID, amount);
    }

    public static void makeDonation(final APIResponseHandler responseHandler) {
        RequestParams params;

        // TODO: Add assert for donation allocation

        params = new RequestParams();
//        params.put(Constants.CAMPAIGN_ID, donation.getCampaignID());
//        params.put(Constants.PARAM_DONATION_AMOUNT, donation.getAmount());
//        params.put(Constants.PARAM_CREDIT_CARD_ID, donation.getCreditCardID());
        params.put(Constants.CAMPAIGN_ID, new Integer(28));
        params.put(Constants.PARAM_DONATION_AMOUNT, new Double(10));
        params.put(Constants.PARAM_CREDIT_CARD_ID, donation.getCreditCardID());

        APIClient.post(Constants.ENDPOINT_DONATE, params, new JsonHttpResponseHandler() {
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
