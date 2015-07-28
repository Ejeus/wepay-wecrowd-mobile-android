package internal;

import android.content.Context;

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

    private static void initializeMembersFromContext(Context context) {
        if (config == null) {
            config = new Config(context, "58670", Config.ENVIRONMENT_STAGING); //  116876 // My app: 58670
        }

        if (wepay == null) {
            wepay = new WePay(config);
        }
    }
}
