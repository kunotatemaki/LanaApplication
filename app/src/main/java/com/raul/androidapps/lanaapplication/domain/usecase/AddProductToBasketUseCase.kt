package com.raul.androidapps.lanaapplication.domain.usecase

import com.raul.androidapps.lanaapplication.repository.Repository


class AddProductToBasketUseCase constructor(private val repository: Repository) {

    suspend fun addProductToBasket(code: String) =
        repository.addProductToBasket(code)

}