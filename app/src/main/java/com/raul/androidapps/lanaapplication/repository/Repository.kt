package com.raul.androidapps.lanaapplication.repository

import com.raul.androidapps.lanaapplication.domain.NetworkResponse

interface Repository {

    suspend fun getFoo(): NetworkResponse<Any>

}