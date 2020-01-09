package com.raul.androidapps.lanaapplication.network

import com.raul.androidapps.lanaapplication.domain.Products
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AppApi {

    @GET("bins/4bwec")
    suspend fun getProducts(): Response<Products>

}
