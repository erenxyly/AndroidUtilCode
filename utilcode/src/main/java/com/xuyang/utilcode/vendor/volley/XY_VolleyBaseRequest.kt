package com.xuyang.utilcode.vendor.volley

import android.content.Context
import com.android.volley.Request

/**
 * Created by XuYang
 * 2019-11-04
 * Email:544066591@qq.com
 */
open class XY_VolleyBaseRequest protected constructor(private val mContext: Context) {

    protected fun addRequest(request: Request<*>) {
        XY_Volley.getInstance(mContext)?.add(request)
    }
}
