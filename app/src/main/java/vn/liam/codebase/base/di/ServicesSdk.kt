package vn.liam.codebase.base.di

import android.annotation.SuppressLint
import com.intuit.sdp.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import vn.liam.codebase.utils.JsonUtil
import java.net.SocketTimeoutException
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

interface IService
object ServicesSdk {

    fun <IService> apiBuilderProvide(host: String = getHost(), apiClass: Class<IService>): IService {
        return ServicesSdk.retrofitInstance(host).create(apiClass)
    }

    private fun retrofitInstance(host: String = getHost()): Retrofit {
        val okHttpClient: OkHttpClient = headersInjectedHTTPClient
        return Retrofit.Builder().addConverterFactory(
            GsonConverterFactory.create(
                JsonUtil.instance.gson
            )
        ).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).baseUrl(host)
            .client(okHttpClient).build()
    }


    private fun retrofitNoLog(host: String = getHost()): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(JsonUtil.instance.gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).baseUrl(host).build()
    }

    /**
     * @return HTTP client with headers injected
     */
    private val headersInjectedHTTPClient: OkHttpClient
        get() {
            var builder: OkHttpClient.Builder = OkHttpClient.Builder()
            builder = injectRequestHeadersBuilder(builder)
            if (BuildConfig.DEBUG) {
                builder = injectLoggingBuilder(builder)
            }
            builder = injectUnsafeBuilder(builder)
            builder.interceptors().add(Interceptor { chain -> onOnIntercept(chain) })
            return builder.build()
        }

    private fun onOnIntercept(chain: Interceptor.Chain): Response {
        try {
            return chain.proceed(chain.request().addAuthQuery())
        } catch (exception: SocketTimeoutException) {
            exception.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return chain.proceed(chain.request())
    }

    private fun Request.addAuthQuery(): Request {
        return this.newBuilder()//.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzNzQxMTg0NmNjZjVlNTIwMDIzNzViZTdmYzVmYTdhNSIsInN1YiI6IjY1MGVmZTNkZTFmYWVkMDEwMGU4NTI2ZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Y98uLBlf96JMcdxSRUZnf997gwsZbv0JIoP5_cHLIt4")
            .url(
            this.url.newBuilder()
                .addQueryParameter("api_key", getApiKey())
                .build()
        ).build()
    }

    private fun injectRequestHeadersBuilder(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        builder.addInterceptor { chain ->
            val request: Request = chain.request()
            chain.proceed(request)
        }
        return builder
    }

    private fun injectLoggingBuilder(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        val loggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        builder.addInterceptor(loggingInterceptor)
        return builder
    }

    private val unsafeHostnameVerifier: HostnameVerifier
        get() {
            return HostnameVerifier { hostname, session -> true }
        }

    private fun injectUnsafeBuilder(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        builder.hostnameVerifier(unsafeHostnameVerifier)
        val unsafeTrustManager: X509TrustManager =
            @SuppressLint("CustomX509TrustManager") object : X509TrustManager {
                @SuppressLint("TrustAllX509TrustManager")
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    x509Certificates: Array<X509Certificate>, s: String
                ) {
                }

                @SuppressLint("TrustAllX509TrustManager")
                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    x509Certificates: Array<X509Certificate>, s: String
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate?> {
                    return arrayOfNulls(0)
                }
            }
        val trustAllCerts: Array<TrustManager> = arrayOf(unsafeTrustManager)
        var sslContext: SSLContext? = null
        try {
            sslContext = SSLContext.getInstance("SSL")
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        try {
            assert(sslContext != null)
            sslContext!!.init(null, trustAllCerts, SecureRandom())
        } catch (e: KeyManagementException) {
            e.printStackTrace()
        }
        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory: SSLSocketFactory = sslContext!!.socketFactory
        builder.sslSocketFactory(sslSocketFactory, unsafeTrustManager)
        return builder
    }

    private fun getApiKey(): String = ""
    private fun getHost(): String = ""

}