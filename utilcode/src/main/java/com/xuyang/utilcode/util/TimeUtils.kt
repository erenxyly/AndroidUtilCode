package com.xuyang.utilcode.util

import java.util.*

/**
 * Created by XuYang
 * 2019-11-07
 * Email:544066591@qq.com
 */
object TimeUtils {

    @JvmStatic
    fun millis2StringClock(millis: Long): String {
        return String.format(
            Locale.getDefault(),
            "%02d:%02d:%02d",
            millis / 3600,
            millis % 3600 / 60,
            millis % 60
        )
    }
}
