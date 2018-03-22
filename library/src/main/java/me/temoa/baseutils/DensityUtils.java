package me.temoa.baseutils;

import android.content.res.Resources;

/**
 * Created by lai
 * on 2018/3/19.
 */
@SuppressWarnings("unused")
public class DensityUtils {

    public static int px2dp(float value) {
        float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (value / scale + 0.5F);
    }

    public static int dp2px(float value) {
        float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (value * scale + 0.5F);
    }

    public static int px2sp(float value) {
        float scale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (value / scale + 0.5F);
    }

    public static int sp2px(float value) {
        float scale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (value * scale + 0.5F);
    }
}
