package com.raul.androidapps.lanaapplication.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.raul.androidapps.lanaapplication.persistence.entities.ProductEntity
import com.raul.androidapps.lanaapplication.vo.Result

interface Repository {

    fun getProducts(forceFetchInfo: Boolean = false): LiveData<Result<PagedList<ProductEntity>>>

}