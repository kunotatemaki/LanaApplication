package com.raul.androidapps.testapplication.repository

import com.raul.androidapps.testapplication.domain.NetworkResponse

interface Repository {

    suspend fun getFoo(): NetworkResponse<Any>

}