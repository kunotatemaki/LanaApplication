package com.raul.androidapps.lanaapplication.domain


class Products constructor(val products: List<Item>)

class Item(
    val code: String,
    val name: String,
    val price: Double
)