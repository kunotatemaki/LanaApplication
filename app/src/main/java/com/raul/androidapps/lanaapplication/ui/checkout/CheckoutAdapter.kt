package com.raul.androidapps.lanaapplication.ui.checkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.raul.androidapps.lanaapplication.R
import com.raul.androidapps.lanaapplication.databinding.BindingComponent
import com.raul.androidapps.lanaapplication.databinding.CheckoutItemBinding
import com.raul.androidapps.lanaapplication.databinding.CheckoutTitleBinding
import com.raul.androidapps.lanaapplication.domain.Checkout
import com.raul.androidapps.lanaapplication.resources.ResourcesManager


class CheckoutAdapter(
    private var checkout: Checkout,
    private val checkoutBasketInteractions: CheckoutBasketInteractions,
    private val resourcesManager: ResourcesManager,
    private val bindingComponent: BindingComponent
) :
    RecyclerView.Adapter<CheckoutViewHolder>() {

    enum class CheckoutType(val value: Int) {
        TITLE(0),
        PRODUCT(1),
        DISCOUNT(2)
    }

    override fun getItemViewType(position: Int): Int =
        when (position) {
            0 -> CheckoutType.TITLE.value
            in 1 until (1 + checkout.products.size) -> CheckoutType.PRODUCT.value
            1 + checkout.products.size -> CheckoutType.TITLE.value
            in 2 + checkout.products.size until (2 + checkout.products.size + getDiscountsSize()) -> CheckoutType.DISCOUNT.value
            else -> CheckoutType.TITLE.value
        }

    private fun getDiscountsSize(): Int =
        if (checkout.discounts.isEmpty()) 1 else checkout.discounts.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckoutViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            CheckoutType.PRODUCT.value -> {
                val binding =
                    DataBindingUtil.inflate<CheckoutItemBinding>(
                        inflater,
                        R.layout.checkout_item,
                        parent,
                        false,
                        bindingComponent
                    )
                ProductCheckoutViewHolder(binding, resourcesManager)
            }
            CheckoutType.DISCOUNT.value -> {
                val binding =
                    DataBindingUtil.inflate<CheckoutItemBinding>(
                        inflater,
                        R.layout.checkout_item,
                        parent,
                        false,
                        bindingComponent
                    )
                DiscountCheckoutViewHolder(binding, resourcesManager)
            }
            else -> {
                val binding =
                    DataBindingUtil.inflate<CheckoutTitleBinding>(
                        inflater,
                        R.layout.checkout_title,
                        parent,
                        false,
                        bindingComponent
                    )
                TitleCheckoutViewHolder(binding, resourcesManager)
            }
        }
    }

    // productTitle + products + discountsTitle + (discounts or no_discount) + saving + pay
    override fun getItemCount(): Int {
        val discountsSize = if (checkout.discounts.isEmpty()) 1 else checkout.discounts.size
        return 1 + checkout.products.size + 1 + discountsSize + 1 + 1
    }


    override fun onBindViewHolder(holder: CheckoutViewHolder, position: Int) {
        when (holder) {
            is TitleCheckoutViewHolder -> {
                val title: String
                val price: Double?
                when (position) {
                    0 -> {
                        title = resourcesManager.getString(R.string.products_title)
                        price = null
                    }
                    1 + checkout.products.size -> {
                        title = resourcesManager.getString(R.string.discounts_title)
                        price = null
                    }
                    itemCount - 2 -> {
                        title = resourcesManager.getString(R.string.money_saved)
                        price = 10.0
                    }
                    else -> {
                        title = resourcesManager.getString(R.string.money_to_pay)
                        price = 20.0
                    }
                }
                holder.bind(title, price)
            }
            is DiscountCheckoutViewHolder -> {
                if (checkout.discounts.isEmpty()) {
                    holder.bind(null)
                } else {
                    val discount = checkout.discounts[position - 2 - checkout.products.size]
                    holder.bind(discount)
                }
            }
            is ProductCheckoutViewHolder -> {
                val product = checkout.products[position - 1]
                holder.bind(product)
            }
        }
    }


}