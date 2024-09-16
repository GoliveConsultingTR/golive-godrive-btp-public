package com.golive.godrive.btppublic.util;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;


import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeSuccessDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeWarningDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.golive.godrive.btppublic.R;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Util {

    public static KProgressHUD displayHUD(Activity activity, String title, String message, boolean activeStyle) {

        KProgressHUD hud = KProgressHUD.create(activity);
        if (activeStyle) {
            hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
        } else {
            ImageView imageView = new ImageView(activity);
            hud.setCustomView(imageView);
        }
        hud.setDimAmount(0.5f);
        hud.setLabel(title);
        hud.setDetailsLabel(message);
        hud.setCancellable(false);
        hud.show();

        return hud;
    }
    public static void displayErrorDialog(Activity activity, String title, String message) {

        AwesomeErrorDialog dialog = new AwesomeErrorDialog(activity);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setColoredCircle(R.color.dialogErrorBackgroundColor);
        dialog.setDialogIconAndColor(R.drawable.ic_dialog_error, R.color.colorWhite);
        dialog.setCancelable(false);
        dialog.setButtonBackgroundColor(R.color.dialogErrorBackgroundColor);
        dialog.setButtonText(activity.getString(R.string.ok));
        dialog.setErrorButtonClick(new Closure() {
            @Override
            public void exec() {

                // click
            }
        });
        dialog.show();
    }

    public static void displaySuccessDialog(Activity activity, String title, String message) {

        AwesomeSuccessDialog dialog = new AwesomeSuccessDialog(activity);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setColoredCircle(R.color.dialogSuccessBackgroundColor);
        dialog.setDialogIconAndColor(R.drawable.ic_success, R.color.white);
        dialog.setCancelable(false);
        dialog.setPositiveButtonText(activity.getString(R.string.ok));
        dialog.setPositiveButtonbackgroundColor(R.color.dialogSuccessBackgroundColor);
        dialog.setPositiveButtonTextColor(R.color.white);
        dialog.setPositiveButtonClick(new Closure() {
            @Override
            public void exec() {

            }
        });
        dialog.show();
    }

    public static void displayWarningDialog( Activity activity, String title,String message) {

        AwesomeWarningDialog dialog = new AwesomeWarningDialog(activity);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setColoredCircle(R.color.dialogWarningBackgroundColor);
        dialog.setDialogIconAndColor(R.drawable.ic_dialog_warning, R.color.white);
        dialog.setCancelable(false);
        dialog.setButtonText(activity.getString(R.string.ok));
        dialog.setButtonBackgroundColor(R.color.dialogWarningBackgroundColor);
        dialog.setButtonTextColor(R.color.colorWhite);
        dialog.setWarningButtonClick(new Closure() {
            @Override
            public void exec() {



            }
        });
        dialog.show();
    }

    public static String dateFormat(String dateStr) {

        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        }catch(Exception ex){
            result = "";
            ex.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        result = formatter.format(date);
        return result;

    }

    public static String timeFormat(String dateStr) {

        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        }catch(Exception ex){
            result = "";
            ex.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        result = formatter.format(date);
        return result;

    }

    public static String dateTimeGenericFormat(String dateTimeStr, String fromPattern, String toPattern) {

        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat(fromPattern);
        Date date = null;
        try {
            date = sdf.parse(dateTimeStr);
        }
        catch (Exception ex) {
            result = "";
            ex.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat(toPattern);
        result = formatter.format(date);
        return result;

    }


    public static String getCurrentDateMyFormat() {

        String dateStr = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
        return dateStr;
    }

    public static String getCurrentTimeMyFormat() {

        String timeStr = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
        return timeStr;
    }

    public static void openSoftKeyboard(Activity activity) {
        if (activity != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        }
    }


}
