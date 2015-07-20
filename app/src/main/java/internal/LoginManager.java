package internal;

/**
 * Created by zachv on 7/20/15.
 */

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wepay.wecrowd.wecrowd.R;
import android.content.Context;

import org.apache.http.Header;
import org.json.JSONObject;

public class LoginManager {
    public enum UserType { MERCHANT, PAYER };
    public static UserType userType = UserType.PAYER;

    public static void loginFromContext(Context context,
                                        String email,
                                        String password,
                                        final JsonHttpResponseHandler handler)
    {
        RequestParams params;

        params = new RequestParams();
        params.put(context.getString(R.string.api_email_key), email);
        params.put(context.getString(R.string.api_password_key), password);

        APIClient.post(context.getString(R.string.api_login_endpoint), params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                        userType = UserType.MERCHANT;

                        handler.onSuccess(statusCode, headers, response);
                    }
                }
        );
    }

    public static void logout() {
        // Simulate logout since no actual /logout API call exists
        userType = UserType.PAYER;
    }
}
