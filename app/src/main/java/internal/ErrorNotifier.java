package internal;

import android.app.AlertDialog;
import android.content.Context;

import com.wepay.wecrowd.wecrowd.R;

/**
 * Created by zachv on 7/23/15.
 */
public class ErrorNotifier {
    public static void showSimpleError(Context context,
                                       String errorTitle,
                                       String errorPreface,
                                       String errorMessage)
    {
        AlertDialog.Builder errorBuilder;
        AlertDialog dialog;

        errorBuilder = new AlertDialog.Builder(context);
        errorBuilder.setTitle(errorTitle)
                .setMessage(errorPreface + ". Error: " + errorMessage)
                .setNegativeButton(R.string.dialog_button_close, null);

        dialog = errorBuilder.create();
        dialog.show();
    }
}
