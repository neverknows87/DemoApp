package com.desmond.demoapp.webservice

import com.desmond.demoapp.model.Response
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiService {
    @GET("browse/new-releases")
    fun getAlbumList(@Query("offset")offset: Int,
                     @Query("limit")limit: Int):
            Observable<Response>

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
            val accessToken = "BQADj8N7ytSMGgMRWl2OQV-8zzepO6N5VzURkMMF7XxqxnTZDUMhi2mReWOn8nLfS7xPJDcoP1MvezaeBQzuZDUDdXZRVmytzX0uMcOTkKO-hV3o9QcJwb8ptBqTAE2P_YAwmKq31Eb4Nuz-0OWN"
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