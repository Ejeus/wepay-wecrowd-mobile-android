/**
 * Created by zachv on 7/16/15.
 */

package internal;
import com.loopj.android.http.*;

public class APIClient {
    private static final String URL_BASE = "http://wecrowd.wepay.com/api/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, AsyncHttpResponseHandler responseHandler)
    {
        client.get(getAbsoluteUrl(url), null, responseHandler);
    }

    public static void post(String url,
                            RequestParams params,
                            AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return URL_BASE + relativeUrl;
    }
}
