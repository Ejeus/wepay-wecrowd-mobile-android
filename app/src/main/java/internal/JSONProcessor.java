package internal;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zachv on 7/21/15.
 */
public class JSONProcessor {
    private static final String TAG = "JSON_PROCESSOR";

    public static String stringFromJSON(JSONObject object, String key) {
        String value = null;

        try { value = object.getString(key); }
        catch (JSONException e) { Log.e(TAG, e.getLocalizedMessage()); }

        return value;
    }

    public static Integer integerFromJSON(JSONObject object, String key) {
        Integer value = null;

        try { value = object.getInt(key); }
        catch (JSONException e) { Log.e(TAG, e.getLocalizedMessage()); }

        return value;
    }
}
