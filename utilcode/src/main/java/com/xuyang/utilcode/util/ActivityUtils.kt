package com.xuyang.utilcode.util

import android.app.Activity

/**
 * Created by XuYang
 * 2019-11-04
 * Email:544066591@qq.com
 */
object ActivityUtils {

    @JvmStatic
    fun isActivityAlive(activity: Activity?): Boolean {
        return (activity != null && !activity.isFinishing
                && !activity.isDestroyed)
    }
}