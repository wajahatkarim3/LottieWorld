package com.wajahatkarim3.lottieworld.di.modules

import android.content.Context
import com.wajahatkarim3.lottieworld.common.SecureKeys
import com.wajahatkarim3.lottieworld.data.remote.LottieFilesApiService
import com.wajahatkarim3.lottieworld.data.remote.interceptors.NoConnectionInterceptor
import com.wajahatkarim3.lottieworld.utils.StringUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * The Dagger Module to provide the instances of [OkHttpClient], [Retrofit], and [LottieFilesApiService] classes.
 * @author Wajahat Karim
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun providesOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(NoConnectionInterceptor(context))
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(SecureKeys.baseUrl())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesGithubApiService(retrofit: Retrofit): LottieFilesApiService {
        return retrofit.create(LottieFilesApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideStringUtils(@ApplicationContext context: Context): StringUtils {
        return StringUtils(context)
    }
}