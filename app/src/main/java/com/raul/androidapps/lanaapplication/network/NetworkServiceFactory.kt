package com.raul.androidapps.lanaapplication.network

interface NetworkServiceFactory {

    companion object {
        const val BASE_URL = "https://api.myjson.com/bins/4bwec"

    }

    fun getServiceInstance(): AppApi
}

