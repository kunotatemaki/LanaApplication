package com.raul.androidapps.lanaapplication.network

import retrofit2.Response
import retrofit2.http.GET

interface AppApi {

    @GET("bins/4bwec")
    suspend fun getProducts(): Response<Products>


    //region NETWORK RESPONSE CLASSES
    class Products constructor(val products: List<Item>)
    class Item(
        val code: String,
        val name: String,
        val price: Double
    )

    //endregion
}
