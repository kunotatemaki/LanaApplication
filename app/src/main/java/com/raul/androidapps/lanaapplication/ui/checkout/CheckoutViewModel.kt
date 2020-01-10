package com.raul.androidapps.lanaapplication.ui.checkout

import androidx.lifecycle.*
import com.raul.androidapps.lanaapplication.domain.Checkout
import com.raul.androidapps.lanaapplication.domain.Product
import com.raul.androidapps.lanaapplication.persistence.entities.BasketEntity
import com.raul.androidapps.lanaapplication.persistence.entities.ProductEntity
import com.raul.androidapps.lanaapplication.repository.Repository
import com.raul.androidapps.lanaapplication.resources.ResourcesManager
import com.raul.androidapps.lanaapplication.utils.calculateDiscounts
import kotlinx.coroutines.launch

open class CheckoutViewModel constructor(
    private val repository: Repository,
    private val resourcesManager: ResourcesManager
) : ViewModel() {

    private var checkoutInfo: MediatorLiveData<Checkout> = MediatorLiveData()
    private var productsFromDb: LiveData<List<ProductEntity>> = repository.getProductsFromCache()
    private var productsIbBasket: LiveData<List<BasketEntity>> = repository.getProductsInBasket()


    init {

        checkoutInfo.apply {
            addSource(productsFromDb) {
                getCheckoutInfo()
            }
            addSource(productsIbBasket) {
                getCheckoutInfo()
            }
        }
    }

    private fun getCheckoutInfo() {
        val list = productsFromDb.value?.map { product -> Product.from(product) }
        //add info about how many times the item has been added to the basket
        list?.forEach { product ->
            product.timesInBasket =
                productsIbBasket.value?.firstOrNull { itemInBasket -> itemInBasket.code == product.code }?.selections
                    ?: 0
        }
        //calculate discounts
        val discounts = calculateDiscounts(products = list, resourcesManager = resourcesManager)
        checkoutInfo.value = Checkout(products = list ?: listOf(), discounts = discounts)
    }

    fun getCheckoutAsObservable(): LiveData<Checkout> =
        Transformations.distinctUntilChanged(checkoutInfo)


    fun clearBasket() =
        viewModelScope.launch {
            repository.clearBasket()
        }

}
