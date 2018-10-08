package me.temoa.baseutils;

import android.content.res.Resources;

/**
 * Created by lai
 * on 2018/3/22.
 */

@SuppressWarnings({"WeakerAccess", "unused"}) // public api
public class ScreenUtils {

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
