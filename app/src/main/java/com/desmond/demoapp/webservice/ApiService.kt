package com.desmond.demoapp.webservice

import com.desmond.demoapp.model.Response
import io.reactivex.Observable
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("api/token")
    fun authorize(@Field("client_id") client_id: String,
                  @Field("client_secret")  client_secret: String,
                  @Field("grant_type")  grant_type:String):
            Observable<Response.Token>

    @GET("browse/new-releases")
    fun getAlbumList(@Query("offset")offset: Int,
                     @Query("limit")limit: Int):
            Observable<Response.Result>
}