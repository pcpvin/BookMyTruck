package bmt.android.com.bookmytractor.data.network.model

import android.util.Log
import bmt.android.com.bookmytractor.BuildConfig
import bmt.android.com.bookmytractor.data.constants.NetworkConstants
import bmt.android.com.bookmytractor.data.constants.UserConstants
import bmt.android.com.bookmytractor.data.prefs.UserPrefs
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object ApiController {
    private var retrofit: Retrofit? = null
    private val okHttpClientBuilder = OkHttpClient.Builder()

    val client: Retrofit?
        get()
        {
            val interceptor = Interceptor { chain ->
                val request = chain?.request()?.newBuilder()?.addHeader("x-api-key", "d7f43832eeassadsdasdadsac95a")?.build()
                chain?.proceed(request)
            }

            val interceptorAuth = Interceptor { chain ->
                val request = chain?.request()?.newBuilder()?.addHeader("x-auth-key", " 0756a969asdasdadsadsas14e740b0e")?.build()
                chain?.proceed(request)

            }

            okHttpClientBuilder.addInterceptor(interceptor)
            okHttpClientBuilder.addInterceptor(interceptorAuth)

            if (UserConstants.ACESS_TOKEN.isNotEmpty())
            {
                Log.d("add password",UserConstants.ACESS_TOKEN)
                val interceptorAuth = Interceptor { chain ->
                    val request = chain?.request()?.newBuilder()?.addHeader("x-auth-key", UserConstants.ACESS_TOKEN)?.build()
                     chain?.proceed(request)

                }
                okHttpClientBuilder.addInterceptor(interceptorAuth)
            }

            okHttpClientBuilder.readTimeout(20, TimeUnit.SECONDS).connectTimeout(20, TimeUnit.SECONDS)


            okHttpClientBuilder.addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            })

            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                        .baseUrl(NetworkConstants.API_URL)
                        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()))

                        .client(okHttpClientBuilder.build())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build()
            }

            return this!!.retrofit
        }
}