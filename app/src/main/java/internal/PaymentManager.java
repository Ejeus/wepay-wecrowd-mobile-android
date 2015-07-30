package internal;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.session.MediaSession;

import com.wepay.android.SignatureHandler;
import com.wepay.android.SwiperHandler;
import com.wepay.android.TokenizationHandler;
import com.wepay.android.WePay;
import com.wepay.android.models.Config;
import com.wepay.android.models.PaymentInfo;

/**
 * Created by zachv on 7/27/15.
 */
public class PaymentManager {
    private static final String CLIENT_ID = "116876";
    private static Config config = null;
    private static WePay wepay = null;

    public static void tokenizeInfo(Context context, PaymentInfo paymentInfo,
                                    final TokenizationHandler handler) {
        initializeMembersFromContext(context);

        wepay.tokenize(paymentInfo, handler);
    }

    public static void startCardSwipeTokenization(Context context, SwiperHandler swiperHandler,
                                                  TokenizationHandler tokenizationHandler)
    {
        initializeMembersFromContext(context);

        wepay.startSwiperForTokenizing(swiperHandler, tokenizationHandler);
    }

    public static void startCardSwipeReading(Context context, SwiperHandler swiperHandler) {
        initializeMembersFromContext(context);

        wepay.startSwiperForReading(swiperHandler);
    }

    public static void storeSignatureImage(Context context,
                                           Bitmap image,
                                           String checkoutID,
                                           SignatureHandler signatureHandler)
    {
        initializeMembersFromContext(context);

        wepay.storeSignatureImage(image, checkoutID, signatureHandler);
    }

    private static void initializeMembersFromContext(Context context) {
        config = new Config(context, CLIENT_ID, Config.ENVIRONMENT_STAGING);
        wepay = new WePay(config);
    }
}
