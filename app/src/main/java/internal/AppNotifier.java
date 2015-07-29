package internal;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.wepay.wecrowd.wecrowd.R;

/**
 * Created by zachv on 7/23/15.
 */
public class AppNotifier {
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

    public static void showSimpleSuccess(Context context, CharSequence text) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);

        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
