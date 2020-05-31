package com.xuyang.utilcode.manager

import android.annotation.SuppressLint
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.*

/**
 * Created by XuYang
 * 2019-11-04
 * Email:544066591@qq.com
 */
class HttpsTrustManager : X509TrustManager {

    @SuppressLint("TrustAllX509TrustManager")
    override fun checkClientTrusted(
        x509Certificates: Array<X509Certificate>, s: String
    ) {
    }

    @SuppressLint("TrustAllX509TrustManager")
    override fun checkServerTrusted(
        x509Certificates: Array<X509Certificate>, s: String
    ) {
    }

    override fun getAcceptedIssuers(): Array<X509Certificate> {
        return _AcceptedIssuers
    }

    companion object {

        private val _AcceptedIssuers = arrayOf<X509Certificate>()
        private var trustManagers: Array<TrustManager>? = null

        fun allowAllSSL() {
            HttpsURLConnection.setDefaultHostnameVerifier { arg0, arg1 -> true }

            var context: SSLContext? = null
            if (trustManagers == null) {
                trustManagers = arrayOf(HttpsTrustManager())
            }

            try {
                context = SSLContext.getInstance("TLS")
                context!!.init(null, trustManagers, SecureRandom())
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            } catch (e: KeyManagementException) {
                e.printStackTrace()
            }

            assert(context != null)
            HttpsURLConnection.setDefaultSSLSocketFactory(context!!.socketFactory)
        }
    }
}