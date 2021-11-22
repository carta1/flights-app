package com.jmart.flights_app.data.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.security.cert.CertificateException
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object HttpClient {
    /**
     * This class returns a OkHttpClient builder. You can get a normal one or an unsafe one that allows self-signed certificates
     */

        private const val CONNECT_TIMEOUT = 120L
        private const val READ_TIMEOUT = 120L
        private const val WRITE_TIMEOUT = 90L

        private val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        // for normal connections use this client
        fun getNormalClient() : OkHttpClient.Builder {
            return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addNetworkInterceptor(CustomInterceptor())
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        }

        // for unsafe connections like the testServer, use this client
        fun getUnsafeOkHttpClient(): OkHttpClient.Builder {
            try {
                // Create a trust manager that does not validate certificate chains
                val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(
                        chain: Array<java.security.cert.X509Certificate>,
                        authType: String
                    ) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(
                        chain: Array<java.security.cert.X509Certificate>,
                        authType: String
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                        return arrayOf()
                    }
                })

                // Install the all-trusting trust manager
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, java.security.SecureRandom())

                // Create an ssl socket factory with our all-trusting manager
                val sslSocketFactory = sslContext.socketFactory

                return OkHttpClient.Builder().apply {
                    addInterceptor(loggingInterceptor)
                    addNetworkInterceptor(CustomInterceptor())
                    connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                    sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                    hostnameVerifier { _, _ -> true }
                }
            } catch (e: Exception) {
                throw RuntimeException(e)
            }

        }

    /**
     * Interceptor class to add and retrieve the headers
     */
    class CustomInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain) : Response {

            // add the headers
            val request = chain.request().newBuilder().apply {
                header("Content-Type", "application/json")
                header("Accept", "application/json, text/plain, */*")
            }.build()

            // execute the request
            return chain.proceed(request)
        }
    }

}