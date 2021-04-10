package com.ninositsolution.paging.di

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.ninositsolution.paging.BuildConfig
import com.ninositsolution.paging.networks.APIService
import com.ninositsolution.paging.networks.NetworkConnectionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltProviders {

    @Provides
    fun provideBaseURL() = BuildConfig.BASE_URL

    @Singleton
    @Provides
    fun provideNetworkConnectionInterceptor(@ApplicationContext context: Context) =
        NetworkConnectionInterceptor(context)

    @Singleton
    @Provides
    fun provideOkHttpClient(networkConnectionInterceptor: NetworkConnectionInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .addInterceptor(networkConnectionInterceptor)
            .readTimeout(45, TimeUnit.SECONDS)
            .connectTimeout(45, TimeUnit.SECONDS)
            .addInterceptor {
                val requestBuilder = it.request().newBuilder()
                requestBuilder.header("Content-Type", "application/json")
                requestBuilder.header("Accept", "application/json")
                requestBuilder.header("app-id", "6071662821f1fd2d16e197a0")
                it.proceed(requestBuilder.build())
            }.build()

    @Singleton
    @Provides
    fun provideRetrofitInstance(okHttpClient: OkHttpClient, baseUrl: String): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideAPIService(retrofit: Retrofit): APIService = retrofit.create(APIService::class.java)
}