package com.xuyang.utilcode.vendor.volley

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

/**
 * Created by XuYang
 * 2019-11-04
 * Email:544066591@qq.com
 */
object XY_Volley {

    private var instance: RequestQueue? = null

    internal fun getInstance(context: Context): RequestQueue? {
        if (instance == null) {
            synchronized(RequestQueue::class.java) {
                if (instance == null) {
                    instance = Volley.newRequestQueue(context)
                }
            }
        }
        return instance
    }
}