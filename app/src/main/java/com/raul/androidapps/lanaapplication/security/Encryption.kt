package com.raul.androidapps.lanaapplication.security


interface Encryption {

    fun encryptString(text: String?, alias: String): String
    fun decryptString(text: String?, alias: String): String
}