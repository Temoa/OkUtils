package me.temoa.baseutils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Looper;

import java.util.List;

/**
 * Created by lai
 * on 2018/3/23.
 */

public class ApplicationUtils {

    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    public static boolean isMainProcess(Context context) {
        if (context == null) {
            throw new NullPointerException("this context is null");
        }
        return context.getPackageName().equals(getCurrentProcessName(context));
    }

    public static String getCurrentProcessName(Context context) {
        if (context == null) {
            throw new NullPointerException("this context is null");
        }
        String curProcessName = "";
        int pid = android.os.Process.myPid();
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (manager == null) {
            return null;
        }
        List<ActivityManager.RunningAppProcessInfo> infoList = manager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo processInfo : infoList) {
            if (processInfo.pid == pid) {
                curProcessName = processInfo.processName;
                break;
            }
        }
        return curProcessName;
    }

    public static int getVersionCode(Context context) {
        if (context == null) {
            throw new NullPointerException("this context is null");
        }
        String packageName = context.getPackageName();
        int versionCode = 1;
        try {
            versionCode = getPackageInfo(context, packageName).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public static String getVersionName(Context context) {
        if (context == null) {
            throw new NullPointerException("this context is null");
        }
        String packageName = context.getPackageName();
        String versionName = "0.0.0";
        try {
            versionName = getPackageInfo(context, packageName).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static PackageInfo getPackageInfo(Context context, String packageName)
            throws PackageManager.NameNotFoundException {

        if (context == null) {
            throw new NullPointerException("this context is null");
        }
        return context.getPackageManager().getPackageInfo(packageName, 0);
    }
}
