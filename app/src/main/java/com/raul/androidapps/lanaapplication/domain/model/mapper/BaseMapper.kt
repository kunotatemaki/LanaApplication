package com.raul.androidapps.lanaapplication.domain.model.mapper

interface BaseMapper<in A, out B> {
    fun map(type: A): B
}
 