package com.raul.androidapps.lanaapplication.repository


import androidx.lifecycle.LiveData
import com.raul.androidapps.lanaapplication.network.AppApi.Item
import com.raul.androidapps.lanaapplication.network.AppApi.Products
import com.raul.androidapps.lanaapplication.network.NetworkServiceFactory
import com.raul.androidapps.lanaapplication.persistence.PersistenceManager
import com.raul.androidapps.lanaapplication.persistence.entities.BasketEntity
import com.raul.androidapps.lanaapplication.persistence.entities.ProductEntity
import com.raul.androidapps.lanaapplication.preferences.PreferencesConstants
import com.raul.androidapps.lanaapplication.preferences.PreferencesManager
import com.raul.androidapps.lanaapplication.utils.RateLimiter
import com.raul.androidapps.lanaapplication.vo.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RepositoryImpl @Inject constructor(
    private val networkServiceFactory: NetworkServiceFactory,
    private val persistenceManager: PersistenceManager,
    private val preferencesManager: PreferencesManager,
    private val rateLimiter: RateLimiter
) :
    Repository {
    private val timeout: Int = 1
    private val timeUnit: TimeUnit = TimeUnit.MINUTES

    override fun getProducts(forceFetchInfo: Boolean): LiveData<Result<List<ProductEntity>>> =
        resultLiveData(
            databaseQuery = {
                getProductsFromCache()
            },
            networkCall = {
                fetchFromNetwork()
            },
            saveCallResult = {
                saveToDb(it?.products ?: listOf())
            },
            runNetworkCall = (forceFetchInfo || shouldFetch()).also { willFetch ->
                if (willFetch) {
                    storeLastFetchedTimestamp()
                }
            }
        )

    override fun getProductsInBasket(): LiveData<List<BasketEntity>> =
        persistenceManager.getProductsInBasket()

    override suspend fun addProductToBasket(code: String) =
        withContext(Dispatchers.IO) {
            persistenceManager.addProductToBasket(code)
        }

    override suspend fun removeProductFromBasket(code: String) =
        withContext(Dispatchers.IO) {
            persistenceManager.removeProductFromBasket(code)
        }

    override suspend fun clearBasket() =
        withContext(Dispatchers.IO) {
            persistenceManager.clearBasket()
        }

    override fun getProductsFromCache(): LiveData<List<ProductEntity>>  =
        persistenceManager.getProducts()

    private fun shouldFetch(): Boolean {
        val lastFetched: Long =
            preferencesManager.getLongFromPreferences(PreferencesConstants.LAST_FETCHED)
        return rateLimiter.shouldFetch(lastFetched, timeout, timeUnit)
    }

    private fun storeLastFetchedTimestamp() {
        preferencesManager.setLongIntoPreferences(
            PreferencesConstants.LAST_FETCHED,
            System.currentTimeMillis()
        )
    }

    private suspend fun fetchFromNetwork(): Result<Products> {
        return try {
            val resp = networkServiceFactory.getServiceInstance().getProducts()
            if (resp.isSuccessful && resp.body() != null) {
                Result.success(resp.body())
            } else {
                Result.error(resp.message())
            }
        } catch (e: Exception) {
            Result.error("error fetching from network")
        }
    }

    private suspend fun saveToDb(products: List<Item>) {
        persistenceManager.storeProducts(products = products)
    }

}