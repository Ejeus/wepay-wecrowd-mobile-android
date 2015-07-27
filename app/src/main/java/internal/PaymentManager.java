package internal;

import android.content.Context;
import android.location.Address;

import com.wepay.android.TokenizationHandler;
import com.wepay.android.WePay;
import com.wepay.android.enums.PaymentMethod;
import com.wepay.android.models.Config;
import com.wepay.android.models.PaymentInfo;

/**
 * Created by zachv on 7/27/15.
 */
public class PaymentManager {
    private static Config config = null;
    private static WePay wepay = null;


    public static void TokenizeRawInfoManual(Context context, String firstName, String lastName,
                                             String email, String paymentDescription,
                                             Address billingAddress, Address shippingAddress,
                                             String ccNumber, String cvv, String expMonth,
                                             String expYear, boolean virtualTerminal,
                                             final TokenizationHandler handler) {
        PaymentInfo paymentInfo;
        initializeMembersFromContext(context);

        paymentInfo = new PaymentInfo(firstName, lastName, email, paymentDescription,
                billingAddress, shippingAddress, PaymentMethod.MANUAL, ccNumber, cvv, expMonth,
                expYear, virtualTerminal);

        wepay.tokenize(paymentInfo, handler);
    }

    private static void initializeMembersFromContext(Context context) {
        if (config == null) {
            config = new Config(context, "116876", Config.ENVIRONMENT_STAGING);
        }

        if (wepay == null) {
            wepay = new WePay(config);
        }
    }
}
