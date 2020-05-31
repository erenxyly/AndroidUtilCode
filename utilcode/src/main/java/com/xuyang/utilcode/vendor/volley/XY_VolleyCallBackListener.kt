package com.xuyang.utilcode.vendor.volley

import com.android.volley.VolleyError

/**
 * Created by XuYang
 * 2019-11-04
 * Email:544066591@qq.com
 */
interface XY_VolleyCallBackListener<T> {

    fun onSuccessResponse(response: T)

    fun onErrorResponse(error: VolleyError)
}
