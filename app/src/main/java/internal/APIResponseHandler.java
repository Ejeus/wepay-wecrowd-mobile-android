package internal;

import android.graphics.Bitmap;

import models.*;

/**
 * Created by zachv on 7/21/15.
 */
public class APIResponseHandler {
    public void onCompletion(Campaign campaign, Throwable throwable) {}
    public void onCompletion(User user, Throwable throwable) {}
    public void onCompletion(Campaign[] campaigns, Throwable throwable) {}
    public void onCompletion(Bitmap bitmap, Throwable throwable) {}
}
