package com.raul.androidapps.lanaapplication.repository


import com.raul.androidapps.lanaapplication.network.NetworkServiceFactory
import com.raul.androidapps.lanaapplication.domain.NetworkResponse
import com.raul.androidapps.lanaapplication.domain.Products
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RepositoryImpl @Inject constructor(private val networkServiceFactory: NetworkServiceFactory) :
    Repository {
    override suspend fun getProducts(): NetworkResponse<Products> {
        return try {
            val resp = networkServiceFactory.getServiceInstance().getProducts()
            if (resp.isSuccessful && resp.body() != null) {
                NetworkResponse.Success(resp.body()!!)
            } else {
                NetworkResponse.Failure(resp.message())
            }
        } catch (e: Exception) {
            NetworkResponse.Failure("error fetching from network")
        }
    }


}