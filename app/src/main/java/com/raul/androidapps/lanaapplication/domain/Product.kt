package com.raul.androidapps.lanaapplication.domain

import com.raul.androidapps.lanaapplication.persistence.entities.ProductEntity

data class Product (
    val code: String,
    val name: String,
    var price: Double,
    var timesInBasket: Int = 0, // used for calculation of discounts
    var allowOffersInItem: Boolean = true //used for non accumulative offers (if required)
){

    companion object {
        fun from(item: ProductEntity): Product =
            Product(code = item.code, name = item.name, price = item.price)
    }
}
