package com.raul.androidapps.lanaapplication.utils

import com.raul.androidapps.lanaapplication.R
import com.raul.androidapps.lanaapplication.domain.Discount
import com.raul.androidapps.lanaapplication.domain.Product
import com.raul.androidapps.lanaapplication.preferences.PreferencesManager
import com.raul.androidapps.lanaapplication.resources.ResourcesManager

fun calculateDiscounts(products: List<Product>, resourcesManager: ResourcesManager): List<Discount> =
    listOf(
        marketingDiscount(products, resourcesManager),
        cfoDiscount(products, resourcesManager)
    )

// region discount per companies
/** 2-for-1 on `PEN` items -> half of the items are free.
 *  If number of items is odd, half are free: 4 items -> 2 free
 *  If number of items is even, half (rounded down) are free: 5 items -> 2.5 half -> 2 free
 */
fun marketingDiscount(products: List<Product>, resourcesManager: ResourcesManager): Discount {
    val penItems = products.filter { it.code == "PEN" }
    val freeItems: Int = penItems.size / 2
    val description = resourcesManager.getString(R.string.marketing_discount_description)
    val pricePerUnit = penItems.firstOrNull()?.price ?: 0.0
    return Discount(description, freeItems * pricePerUnit)
}

fun cfoDiscount(products: List<Product>, resourcesManager: ResourcesManager): Discount {
    val penItems = products.filter { it.code == "PEN" }
    val freeItems: Int = penItems.size / 2
    val description = resourcesManager.getString(R.string.cfo_discount_description)
    val pricePerUnit = penItems.firstOrNull()?.price ?: 0.0
    return Discount(description, freeItems * pricePerUnit)
}


//endregion