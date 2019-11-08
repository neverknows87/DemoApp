package com.desmond.demoapp.webservice

import com.desmond.demoapp.ACCESSTOKEN
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val TIMEOUT:Long = 20
class RequestFactory {
    companion object {
        fun createAuthService(): ApiService {
            val retrofit = makeAuthRetrofit()

            return retrofit.create(ApiService::class.java)
        }

        fun createApiService(): ApiService {
            val retrofit = makeApiRetrofit(accessTokenProvidingInterceptor())

            return retrofit.create(ApiService::class.java)
        }

        fun makeAuthRetrofit() = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://accounts.spotify.com/")
            .client(makeAuthHttpClient())
            .build()

        fun makeApiRetrofit(vararg interceptors: Interceptor) = Retrofit.Builder()
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
            chain.proceed(chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $ACCESSTOKEN")
                .build())
        }

        private fun makeHttpClient(interceptors: Array<out Interceptor>) = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(headersInterceptor())
            .addInterceptor(HttpLoggingInterceptor().apply {
                level =  HttpLoggingInterceptor.Level.BODY
            })
            .apply { interceptors().addAll(interceptors)
            }
            .build()

        private fun makeAuthHttpClient() = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level =  HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }
}