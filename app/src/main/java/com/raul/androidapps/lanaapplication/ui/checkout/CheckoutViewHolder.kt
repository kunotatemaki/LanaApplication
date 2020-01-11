package com.raul.androidapps.lanaapplication.ui.checkout

import android.annotation.SuppressLint
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.raul.androidapps.lanaapplication.R
import com.raul.androidapps.lanaapplication.databinding.CheckoutItemBinding
import com.raul.androidapps.lanaapplication.databinding.CheckoutTitleBinding
import com.raul.androidapps.lanaapplication.domain.Checkout
import com.raul.androidapps.lanaapplication.domain.Discount
import com.raul.androidapps.lanaapplication.domain.Product
import com.raul.androidapps.lanaapplication.resources.ResourcesManager


sealed class CheckoutViewHolder(
    binding: ViewDataBinding,
    protected val resourcesManager: ResourcesManager
) : RecyclerView.ViewHolder(binding.root)

class TitleCheckoutViewHolder(
    private val binding: CheckoutTitleBinding,
    resourcesManager: ResourcesManager
) : CheckoutViewHolder(binding, resourcesManager) {
    @SuppressLint("SetTextI18n")
    fun bind(title: String) {
        binding.title = title
        binding.showPrice = false
    }
}

class SavingCheckoutViewHolder(
    private val binding: CheckoutTitleBinding,
    resourcesManager: ResourcesManager
) : CheckoutViewHolder(binding, resourcesManager) {
    @SuppressLint("SetTextI18n")
    fun bind(title: String, checkout: Checkout) {
        binding.title = title
        binding.showPrice = true
        binding.checkoutTitlePrice.text = "${checkout.discounts.sumByDouble { it.savedMoney }}€"
    }
}

class TotalCheckoutViewHolder(
    private val binding: CheckoutTitleBinding,
    resourcesManager: ResourcesManager
) : CheckoutViewHolder(binding, resourcesManager) {
    @SuppressLint("SetTextI18n")
    fun bind(title: String, checkout: Checkout) {
        binding.title = title
        binding.showPrice = true
        binding.checkoutTitlePrice.text =
            "${checkout.products.sumByDouble { it.price * it.timesInBasket } - checkout.discounts.sumByDouble { it.savedMoney }}€"
    }
}

class ProductCheckoutViewHolder(
    private val binding: CheckoutItemBinding,
    resourcesManager: ResourcesManager
) : CheckoutViewHolder(binding, resourcesManager) {
    @SuppressLint("SetTextI18n")
    fun bind(product: Product) {
        val title = resourcesManager.getString(R.string.product_checkout)
        binding.title = String.format(title, product.timesInBasket, product.name)
        binding.showPrice = true
        binding.checkoutItemPrice.text = "${product.price * product.timesInBasket}€"
    }
}

class DiscountCheckoutViewHolder(
    private val binding: CheckoutItemBinding,
    resourcesManager: ResourcesManager
) : CheckoutViewHolder(binding, resourcesManager) {
    fun bind(discount: Discount?) {
        if (discount == null) {
            binding.title = resourcesManager.getString(R.string.discount_checkout_empty)
            binding.showPrice = false
        } else {
            binding.title = discount.description
            binding.showPrice = true
            binding.checkoutItemPrice.text = "${discount.savedMoney}€"
        }
    }

}
