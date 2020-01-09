package com.raul.androidapps.lanaapplication.repository

import androidx.lifecycle.LiveData
import com.raul.androidapps.lanaapplication.persistence.entities.ProductEntity
import com.raul.androidapps.lanaapplication.vo.Result

interface Repository {

    fun getProducts(forceFetchInfo: Boolean = false): LiveData<Result<List<ProductEntity>>>

}