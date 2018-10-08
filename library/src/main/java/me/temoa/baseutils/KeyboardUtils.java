package me.temoa.baseutils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by lai
 * on 2018/3/21.
 */

@SuppressWarnings({"WeakerAccess", "unused"}) // public api
public class KeyboardUtils {

    public static void showSoftInput(View view) {
        if (view == null) {
            throw new NullPointerException("the view is null");
        }
        Context context = view.getContext();
        if (context == null) {
            return;
        }
        InputMethodManager manager
                = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager == null) {
            return;
        }
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        manager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    public static void hideSoftInput(View view) {
        if (view == null) {
            throw new NullPointerException("the view is null");
        }
        Context context = view.getContext();
        InputMethodManager manager
                = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager == null) {
            return;
        }
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void toggleSoftInput(Context context) {
        InputMethodManager manager
                = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager == null) {
            return;
        }
        manager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }
}
