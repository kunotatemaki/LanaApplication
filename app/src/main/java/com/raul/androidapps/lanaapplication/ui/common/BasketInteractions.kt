package com.raul.androidapps.lanaapplication.ui.common


interface BasketInteractions {
    fun addProductToBasket(code: String?)
    fun removeProductToBasket(code: String?)
    fun clearBasket()
}