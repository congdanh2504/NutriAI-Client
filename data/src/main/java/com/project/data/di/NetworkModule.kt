package com.project.data.di

import android.content.Context
import com.chibatching.kotpref.BuildConfig
import com.project.data.source.remote.AppApi
import com.project.data.source.remote.AuthInterceptor
import com.project.data.source.remote.TokenAuthenticator
import com.project.data.utils.NetworkConstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideAppApi(client: OkHttpClient): AppApi {
        return Retrofit.Builder()
            .baseUrl(NetworkConstant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(AppApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthInterceptor(): AuthInterceptor {
        return AuthInterceptor()
    }

    @Singleton
    @Provides
    fun provideTokenAuthenticator(): TokenAuthenticator {
        return TokenAuthenticator(
            Retrofit.Builder()
                .baseUrl(NetworkConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AppApi::class.java)
        )
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        authInterceptor: AuthInterceptor,
        tokenAuthenticator: TokenAuthenticator
    ): OkHttpClient {
        val myCache = Cache(context.cacheDir, NetworkConstant.CACHE_SIZE)

        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }

        val builder = OkHttpClient.Builder()
            .cache(myCache)
            .addInterceptor(authInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .authenticator(tokenAuthenticator)
            .connectTimeout(NetworkConstant.TIME_OUT, TimeUnit.MILLISECONDS)
            .writeTimeout(NetworkConstant.TIME_OUT, TimeUnit.MILLISECONDS)
            .readTimeout(NetworkConstant.TIME_OUT, TimeUnit.MILLISECONDS)
        return builder.build()
    }
}