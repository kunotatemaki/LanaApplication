package com.raul.androidapps.lanaapplication.domain

import com.raul.androidapps.lanaapplication.resources.ResourcesManager
import com.raul.androidapps.lanaapplication.utils.calculateDiscounts


class Checkout constructor(val products: List<Product>, resourcesManager: ResourcesManager){

    val discounts: List<Discount> = calculateDiscounts(products, resourcesManager)

}