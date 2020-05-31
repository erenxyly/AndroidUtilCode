package com.xuyang.utilcode.vendor.volley

import android.content.Context
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import org.json.JSONObject
import java.util.HashMap

/**
 * Created by XuYang
 * 2019-11-04
 * Email:544066591@qq.com
 */
class XY_VolleyRequest<T>(context: Context) : XY_VolleyBaseRequest(context) {

    fun stringRequest(
        method: Int,
        url: String,
        params: Map<String, String>?,
        header: Map<String, String>?,
        timeout: Int,
        listener: XY_VolleyCallBackListener<T>?
    ) {
        val stringRequest = object : StringRequest(method, url,
            Response.Listener { response ->
                listener?.onSuccessResponse(response as T)
            }, Response.ErrorListener { error ->
                listener?.onErrorResponse(error)
            }) {
            override fun getParams(): Map<String, String> {
                return params ?: HashMap()
            }

            override fun getHeaders(): Map<String, String> {
                return header ?: HashMap()
            }
        }
        stringRequest.retryPolicy =
            DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
        addRequest(stringRequest)
    }

    fun jsonObjectRequest(
        method: Int,
        url: String,
        jsonObject: JSONObject,
        header: Map<String, String>?,
        timeout: Int,
        listener: XY_VolleyCallBackListener<T>?
    ) {
        val jsonObjectRequest = object : JsonObjectRequest(method, url, jsonObject,
            Response.Listener { response ->
                listener?.onSuccessResponse(response as T)
            }, Response.ErrorListener { error ->
                listener?.onErrorResponse(error)
            }) {
            override fun getHeaders(): Map<String, String> {
                return header ?: HashMap()
            }
        }
        jsonObjectRequest.retryPolicy =
            DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
        addRequest(jsonObjectRequest)
    }
}