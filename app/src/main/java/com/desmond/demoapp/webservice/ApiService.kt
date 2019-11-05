package com.desmond.demoapp.webservice

import com.desmond.demoapp.model.Response
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("browse/new-releases")
    fun getAlbumList(@Query("offset")offset: Int,
                     @Query("limit")limit: Int):
            Observable<Response.Result>
}