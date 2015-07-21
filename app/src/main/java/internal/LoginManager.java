package internal;

/**
 * Created by zachv on 7/20/15.
 */

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wepay.wecrowd.wecrowd.R;
import android.content.Context;
import android.util.Log;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import models.User;

public class LoginManager {
    public enum UserType { MERCHANT, PAYER }
    public static UserType userType = UserType.PAYER;
    public static User user = null;

    public static void loginFromContext(Context context,
                                        String email,
                                        String password,
                                        final APIResponseHandler handler)
    {
        RequestParams params;

        params = new RequestParams();
        params.put(context.getString(R.string.api_email_key), email);
        params.put(context.getString(R.string.api_password_key), password);

        APIClient.post(context.getString(R.string.api_login_endpoint),
                params,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Integer userID;
                        String userToken;
                        Throwable throwable = null;

                        super.onSuccess(statusCode, headers, response);

                        // Retrieve the data from JSON
                        try { userID = response.getInt("user_id"); }
                        catch (JSONException e) {
                            Log.e(LoginManager.class.getName(), "JSON: Unable to retrieve user_id.");
                            throwable = new Throwable(e.getLocalizedMessage());
                            handler.onCompletion(user, throwable);
                            return;
                        }

                        try { userToken = response.getString("token"); }
                        catch (JSONException e) {
                            Log.e(LoginManager.class.getName(), "JSON: Unable to retrieve token.");
                            throwable = new Throwable(e.getLocalizedMessage());
                            handler.onCompletion(user, throwable);
                            return;
                        }

                        user = new User(userID, userToken);

                        userType = UserType.MERCHANT;

                        handler.onCompletion(user, throwable);
                    }

                    @Override
                    public void onFailure(int statusCode,
                                          Header[] headers,
                                          String responseString,
                                          Throwable throwable)
                    {
                        super.onFailure(statusCode, headers, responseString, throwable);

                        handler.onCompletion(user, throwable);
                    }
                }
        );
    }

    public static void logout() {
        // Simulate logout since no actual /logout API call exists
        userType = UserType.PAYER;
        user = null;
    }
}
