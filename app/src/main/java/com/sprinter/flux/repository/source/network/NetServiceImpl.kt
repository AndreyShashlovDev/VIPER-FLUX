package com.sprinter.flux.repository.source.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sprinter.flux.BuildConfig
import com.sprinter.flux.repository.source.network.api.GithubApi
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

internal class NetServiceImpl : NetService {

    companion object {
        private val SCHEDULER_SINGLE_THREAD = Schedulers.from(
            Executors.newSingleThreadExecutor()
        )
    }

    override fun getGithubApi(): GithubApi {
        return getServiceAdapter(
            BuildConfig.GITHUB_API_URL
        ).create(GithubApi::class.java)
    }

    private fun getGson(): Gson = GsonBuilder().create()

    private fun getServiceAdapter(
        url: String,
        vararg interceptors: Interceptor
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .addCallAdapterFactory(
                RxJava2CallAdapterFactory.createWithScheduler(
                    SCHEDULER_SINGLE_THREAD
                )
            )
            .addConverterFactory(GsonConverterFactory.create(getGson()))
            .client(getClient(*interceptors))
            .build()
    }

    private fun getClient(vararg interceptors: Interceptor): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.BASIC

        val builder = OkHttpClient.Builder()
        builder.addInterceptor(logging)

        interceptors.forEach { interceptor -> builder.addInterceptor(interceptor) }

        builder.connectTimeout(BuildConfig.CONNECTION_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(BuildConfig.CONNECTION_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(BuildConfig.CONNECTION_TIME_OUT, TimeUnit.SECONDS)
        return builder.build()
    }
}
