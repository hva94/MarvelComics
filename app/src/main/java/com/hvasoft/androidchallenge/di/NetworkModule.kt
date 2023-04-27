package com.hvasoft.androidchallenge.di

import com.hvasoft.androidchallenge.BuildConfig
import com.hvasoft.androidchallenge.core.Constants.BASE_URL
import com.hvasoft.androidchallenge.core.Constants.ORDER_BY_PARAM
import com.hvasoft.androidchallenge.core.Constants.PRIVATE_KEY_PARAM
import com.hvasoft.androidchallenge.core.Constants.PUBLIC_KEY_PARAM
import com.hvasoft.androidchallenge.core.Constants.TS_PARAM
import com.hvasoft.androidchallenge.core.utils.toMD5
import com.hvasoft.androidchallenge.data.remote_db.ApiHelper
import com.hvasoft.androidchallenge.data.remote_db.ApiHelperImpl
import com.hvasoft.androidchallenge.data.remote_db.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        val interceptor = Interceptor { chain: Interceptor.Chain ->
                val originalRequest = chain.request()
                val originalUrl = originalRequest.url
                val newUrl = originalUrl.newBuilder()
                    .addQueryParameter("orderBy", ORDER_BY_PARAM)
                    .addQueryParameter("ts", TS_PARAM)
                    .addQueryParameter("apikey", PUBLIC_KEY_PARAM)
                    .addQueryParameter("hash", (TS_PARAM + PRIVATE_KEY_PARAM + PUBLIC_KEY_PARAM).toMD5())
                    .build()
                val requestBuilder = originalRequest.newBuilder().url(newUrl)
            return@Interceptor chain.proceed(requestBuilder.build())
             }
        builder.addInterceptor(interceptor)
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
            builder.addInterceptor(loggingInterceptor)
        }
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper
}