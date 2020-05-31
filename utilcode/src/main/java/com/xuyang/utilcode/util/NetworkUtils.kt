package com.xuyang.utilcode.util

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by XuYang
 * 2019-11-21
 * Email:544066591@qq.com
 */
object NetworkUtils {

    @JvmStatic
    fun isNetworkConnected(context: Context?): Boolean {
        if (context != null) {
            val mConnectivityManager = (context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
            val mNetworkInfo = mConnectivityManager.activeNetworkInfo
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable
            }
        }
        return false
    }

    @JvmStatic
    fun isWifiConnected(context: Context?): Boolean {
        if (context != null) {
            val mConnectivityManager = (context!!
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)!!
            val mWiFiNetworkInfo = mConnectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable
            }
        }
        return false
    }

    @JvmStatic
    fun isMobileConnected(context: Context?): Boolean {
        if (context != null) {
            val mConnectivityManager = (context!!
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)!!
            val mMobileNetworkInfo = mConnectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable
            }
        }
        return false
    }

    @JvmStatic
    fun getConnectedType(context: Context?): Int {
        if (context != null) {
            val mConnectivityManager = (context!!
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)!!
            val mNetworkInfo = mConnectivityManager.activeNetworkInfo
            if (mNetworkInfo != null && mNetworkInfo.isAvailable) {
                return mNetworkInfo.type
            }
        }
        return -1
    }
}