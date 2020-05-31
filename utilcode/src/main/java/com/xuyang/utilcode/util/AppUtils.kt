package com.xuyang.utilcode.util

import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageManager
import java.io.IOException
import java.util.*

/**
 * Created by XuYang
 * 2019-11-04
 * Email:544066591@qq.com
 */
object AppUtils {

    @JvmStatic
    fun getAppVersionName(context: Context): String? {
        try {
            val packageManager = context.packageManager
            val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
            return packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            return null
        }

    }

    @JvmStatic
    fun getProperty(key: String, fileName: String, context: Context): String? {
        try {
            val properties = Properties()
            val assetManager = context.assets
            val inputStream = assetManager.open(fileName)
            properties.load(inputStream)
            return properties.getProperty(key)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

    }

    @JvmStatic
    fun isAppForeground(context: Context): Boolean {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val info = am.runningAppProcesses
        if (info == null || info.size == 0) return false
        for (aInfo in info) {
            if (aInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                if (aInfo.processName == context.getPackageName()) {
                    return true
                }
            }
        }
        return false
    }
}