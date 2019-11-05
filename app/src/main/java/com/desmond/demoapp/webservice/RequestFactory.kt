package com.desmond.demoapp.webservice

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RequestFactory {
    companion object {
        fun create(): ApiService {
            val retrofit = makeRetrofit(accessTokenProvidingInterceptor())

            return retrofit.create(ApiService::class.java)
        }

        fun makeRetrofit(vararg interceptors: Interceptor) = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.spotify.com/v1/")
            .client(makeHttpClient(interceptors))
            .build()

        fun headersInterceptor() = Interceptor { chain ->
            chain.proceed(chain.request().newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .build())
        }

        fun accessTokenProvidingInterceptor() = Interceptor { chain ->
            val accessToken = "BQDycqT_eQFS7p8Dwkpub9tu-CCmZtNwAaul1neE9JNDynha2G_es_107E0I32v4nfpIxwJTAyZAVkr9WDA"
            chain.proceed(chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .build())
        }

        private fun makeHttpClient(interceptors: Array<out Interceptor>) = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(headersInterceptor())
            .apply { interceptors().addAll(interceptors) }
            .build()
    }
}