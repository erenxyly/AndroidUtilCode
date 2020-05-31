package com.xuyang.utilcode.listener

import android.os.SystemClock
import android.view.View

/**
 * Created by XuYang
 * 2019-11-04
 * Email:544066591@qq.com
 */
abstract class CustomClickListener : View.OnClickListener {
    private var mLastClickTime: Long = 0
    private var timeInterval = 1000L

    constructor() {

    }

    constructor(interval: Long) {
        this.timeInterval = interval
    }

    override fun onClick(v: View) {
        val nowTime = SystemClock.elapsedRealtime()
        if (nowTime - mLastClickTime > timeInterval) {
            //单次点击
            onSingleClick()
            mLastClickTime = nowTime
        } else {
            //快速多次点击
            onFastClick()
        }
    }

    protected abstract fun onSingleClick()

    protected abstract fun onFastClick()
}