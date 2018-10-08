package me.temoa.baseutils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.annotation.RequiresPermission;
import android.telephony.TelephonyManager;

/**
 * Created by lai
 * on 2018/3/22.
 */

@SuppressWarnings({"WeakerAccess", "unused"}) // public api
public class NetworkUtils {

    public static void openWifiSetting(Context context) {
        if (context == null) {
            throw new NullPointerException("the content is null");
        }
        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isWifiConnected(Context context) {
        if (context == null) {
            throw new NullPointerException("the content is null");
        }
        NetworkInfo networkInfo = getActiveNetworkInfo(context);
        return networkInfo != null && networkInfo.isConnected()
                && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isNetworkConnect(Context context) {
        if (context == null) {
            throw new NullPointerException("the content is null");
        }
        NetworkInfo networkInfo = getActiveNetworkInfo(context);
        return networkInfo != null && networkInfo.isConnected();
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static int getNetworkType(Context context) {
        if (context == null) {
            throw new NullPointerException("the content is null");
        }
        int networkType = -1;
        NetworkInfo networkInfo = getActiveNetworkInfo(context);
        if (networkInfo == null || !networkInfo.isAvailable()) {
            return networkType;
        }
        if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            networkType = 0; // wifi
        } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            switch (networkInfo.getSubtype()) {
                case TelephonyManager.NETWORK_TYPE_GSM:
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    //2G
                    networkType = 2;
                    break;
                case TelephonyManager.NETWORK_TYPE_TD_SCDMA:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    //3G
                    networkType = 3;
                    break;
                case TelephonyManager.NETWORK_TYPE_IWLAN:
                case TelephonyManager.NETWORK_TYPE_LTE:
                    //4G
                    networkType = 4;
                    break;
                default:
                    String subtypeName = networkInfo.getSubtypeName();
                    if (subtypeName.equalsIgnoreCase("TD-SCDMA")
                            || subtypeName.equalsIgnoreCase("WCDMA")
                            || subtypeName.equalsIgnoreCase("CDMA2000")) {
                        //3G
                        networkType = 3;
                    }
                    break;
            }
        }
        return networkType;
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static NetworkInfo getActiveNetworkInfo(Context context) {
        if (context == null) {
            throw new NullPointerException("the content is null");
        }
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            return connectivityManager.getActiveNetworkInfo();
        }
        return null;
    }
}
