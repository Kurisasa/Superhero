package com.kurisani.superhero.repository.api


import android.util.Log
import com.kurisani.superhero.SuperheroApplication
import com.kurisani.superhero.BuildConfig
import com.kurisani.superhero.rx.RxSchedulers
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class ApiRepository(
    moshi: Moshi,
    rxSchedulers: RxSchedulers
) {

    private val authorizationInterceptor = Interceptor { chain ->
        val packageInfo = SuperheroApplication.instance?.packageManager?.getPackageInfo(
            SuperheroApplication.instance?.packageName!!, 0)
        val original = chain.request()
        val requestBuilder = chain.request().newBuilder().header(
            CLIENT_VERSION, ""+packageInfo?.versionName)
            .method(original.method, original.body)
            .apply {
            }
        chain.proceed(
            requestBuilder.build()
        )
    }

    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        .retryOnConnectionFailure(false) // Prevent duplicate requests being sent to server
        .addInterceptor(authorizationInterceptor)
        .apply {
            if (BuildConfig.DEBUG) addInterceptor(
                HttpLoggingInterceptor().setLevel(
                    HttpLoggingInterceptor.Level.BODY
                )
            )
        }
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(rxSchedulers.subscribeOn()))
        .client(httpClient)
        .build()

    val api: ApiMethods = retrofit.create(ApiMethods::class.java)

    fun checkIsFatalUserAuthError(error: Throwable) {
        Log.d("Error", error.message, error)
    }

    companion object {
        private const val TIMEOUT = 30L
        private const val AUTHORIZATION = "Authorization"
        private const val API_BASE_URL = BuildConfig.API_BASE_URL
        private const val CLIENT_VERSION = "X-CLIENT-APP-VER"
        private const val ACCESS_TOKEN = "X-FIREBASE-TOKEN"
    }
}