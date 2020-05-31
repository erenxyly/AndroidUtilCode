package com.xuyang.utilcode.util

import android.content.Context
import android.widget.Toast

/**
 * Created by XuYang
 * 2019-11-04
 * Email:544066591@qq.com
 */
object ToastUtils {

    private var toast: Toast? = null
    private var oldMsg: String? = null
    private var oneTime: Long = 0
    private var twoTime: Long = 0

    @JvmStatic
    fun showToast(context: Context, msg: String) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
            toast!!.show()
            oneTime = System.currentTimeMillis()
        } else {
            twoTime = System.currentTimeMillis()
            if (msg == oldMsg) {
                if (twoTime - oneTime > Toast.LENGTH_LONG) {
                    toast!!.show()
                }
            } else {
                oldMsg = msg
                toast!!.setText(msg)
                toast!!.show()
            }
        }
        oneTime = twoTime
    }

    @JvmStatic
    fun showLongToast(context: Context, msg: String) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_LONG)
            toast!!.show()
            oneTime = System.currentTimeMillis()
        } else {
            twoTime = System.currentTimeMillis()
            if (msg == oldMsg) {
                if (twoTime - oneTime > Toast.LENGTH_LONG) {
                    toast!!.show()
                }
            } else {
                oldMsg = msg
                toast!!.setText(msg)
                toast!!.show()
            }
        }
        oneTime = twoTime
    }

    @JvmStatic
    fun showToast(context: Context, resId: Int) {
        showToast(context, context.getString(resId))
    }
}