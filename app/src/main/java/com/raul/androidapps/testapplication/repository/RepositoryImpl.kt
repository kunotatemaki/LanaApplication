package com.raul.androidapps.testapplication.repository


import com.raul.androidapps.testapplication.network.NetworkServiceFactory
import com.raul.androidapps.testapplication.domain.NetworkResponse
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RepositoryImpl @Inject constructor(private val networkServiceFactory: NetworkServiceFactory) :
    Repository {
    override suspend fun getFoo(): NetworkResponse<Any> {
        return try {
            val resp = networkServiceFactory.getServiceInstance().getFoo(
                path = "path",
                param1 = 0L,
                param2 = "param2"
            )
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