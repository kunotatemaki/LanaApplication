package com.raul.androidapps.lanaapplication.domain.usecase

import com.raul.androidapps.lanaapplication.repository.Repository


class ClearBasketUseCase constructor(private val repository: Repository) {

    suspend fun clearBasket() {
        repository.clearBasket()
    }

}