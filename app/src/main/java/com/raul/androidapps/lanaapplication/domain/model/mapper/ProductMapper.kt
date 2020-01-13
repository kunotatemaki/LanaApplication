package com.raul.androidapps.lanaapplication.domain.model.mapper

import com.raul.androidapps.lanaapplication.domain.model.Product
import com.raul.androidapps.lanaapplication.network.AppApi
import com.raul.androidapps.lanaapplication.persistence.entities.ProductEntity


object ApiToEntityMapper : BaseMapper<AppApi.Item, ProductEntity> {
    override fun map(type: AppApi.Item): ProductEntity =
        ProductEntity(code = type.code, name = type.name, price = type.price)
}

object EntityToProduct : BaseMapper<ProductEntity, Product> {
    override fun map(type: ProductEntity): Product =
        Product(code = type.code, name = type.name, price = type.price)
}