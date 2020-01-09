package com.raul.androidapps.lanaapplication.domain

sealed class NetworkResponse<out T> {

    data class Success<out T>(val data: T) : NetworkResponse<T>()

    data class Failure<out T>(val message: String?) : NetworkResponse<T>()

}
