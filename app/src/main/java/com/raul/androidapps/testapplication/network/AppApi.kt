package com.raul.androidapps.testapplication.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AppApi {

    @GET("url/{path}")
    suspend fun getFoo(
        @Path("path") path: String,
        @Query("param1") param1: Long,
        @Query("param2") param2: String
    ): Response<Any>


}
