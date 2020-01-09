package com.raul.androidapps.lanaapplication.repository

import com.raul.androidapps.lanaapplication.domain.NetworkResponse
import com.raul.androidapps.lanaapplication.domain.Products

interface Repository {

    suspend fun getProducts(): NetworkResponse<Products>

}