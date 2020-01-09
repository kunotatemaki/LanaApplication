package com.raul.androidapps.lanaapplication.domain

import com.raul.androidapps.lanaapplication.persistence.entities.ProductEntity


class Products constructor(val products: List<Item>)

class Item(
    val code: String,
    val name: String,
    val price: Double
) {

}
