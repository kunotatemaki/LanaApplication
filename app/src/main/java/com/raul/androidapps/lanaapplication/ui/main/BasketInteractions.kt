package com.raul.androidapps.lanaapplication.ui.main


interface BasketInteractions {
    fun addProductToBasket(code: String?)
    fun removeProductToBasket(code: String?)
    fun clearBasket()
}