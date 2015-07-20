package internal;

/**
 * Created by zachv on 7/20/15.
 */

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wepay.wecrowd.wecrowd.R;
import android.content.Context;

public class LoginManager {
    public static void loginFromContext(Context context,
                                        String email,
                                        String password,
                                        final JsonHttpResponseHandler handler)
    {
        RequestParams params;

        params = new RequestParams();
        params.put(context.getString(R.string.api_email_key), email);
        params.put(context.getString(R.string.api_password_key), password);

        APIClient.post(context.getString(R.string.api_login_endpoint), params, handler);
    }
}
