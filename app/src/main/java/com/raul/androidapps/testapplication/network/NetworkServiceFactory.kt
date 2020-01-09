package com.raul.androidapps.testapplication.network

interface NetworkServiceFactory {

    companion object {
        const val BASE_URL = "https://api.myjson.com/bins/4bwec"

    }

    fun getServiceInstance(): AppApi
}

