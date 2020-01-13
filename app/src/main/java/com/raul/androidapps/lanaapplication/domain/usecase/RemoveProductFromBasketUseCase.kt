package com.raul.androidapps.lanaapplication.domain.usecase

import com.raul.androidapps.lanaapplication.repository.Repository


class RemoveProductFromBasketUseCase(private val repository: Repository) {

    suspend fun removeProductFromBasket(code: String) =
        repository.removeProductFromBasket(code)

}