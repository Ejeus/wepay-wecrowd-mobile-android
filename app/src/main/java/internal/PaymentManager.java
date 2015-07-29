package internal;

import android.content.Context;
import android.media.session.MediaSession;

import com.wepay.android.SwiperHandler;
import com.wepay.android.TokenizationHandler;
import com.wepay.android.WePay;
import com.wepay.android.models.Config;
import com.wepay.android.models.PaymentInfo;

/**
 * Created by zachv on 7/27/15.
 */
public class PaymentManager {
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

    private static void initializeMembersFromContext(Context context) {
        config = new Config(context, "116876", Config.ENVIRONMENT_STAGING);
        wepay = new WePay(config);
    }
}
