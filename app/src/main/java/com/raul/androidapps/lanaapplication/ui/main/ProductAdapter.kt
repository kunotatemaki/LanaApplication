package com.raul.androidapps.lanaapplication.ui.main

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raul.androidapps.lanaapplication.R
import com.raul.androidapps.lanaapplication.databinding.BindingComponent
import com.raul.androidapps.lanaapplication.databinding.ProductRowBinding
import com.raul.androidapps.lanaapplication.domain.Product
import com.raul.androidapps.lanaapplication.resources.ResourcesManager


class ProductAdapter(
    private val bindingComponent: BindingComponent,
    private val resourcesManager: ResourcesManager,
    private val basketInteractions: BasketInteractions
) :
    ListAdapter<Product, ProductAdapter.ProductViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ProductRowBinding>(
                inflater,
                R.layout.product_row,
                parent,
                false,
                bindingComponent
            )
        return ProductViewHolder(binding, resourcesManager, basketInteractions)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product: Product? = getItem(position)

        holder.bindTo(product)
    }

    class ProductViewHolder(
        private val binding: ProductRowBinding,
        private val resourcesManager: ResourcesManager,
        private val basketInteractions: BasketInteractions
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindTo(product: Product?) {
            binding.product = product
            binding.removeButton.apply {
                setOnClickListener {
                    basketInteractions.removeProductToBasket(product?.code)
                }
                if (product?.timesInBasket ?: 0 > 0) {
                    isEnabled = true
                    supportBackgroundTintList =
                        ColorStateList.valueOf(resourcesManager.getColor(R.color.colorAccent))
                } else {
                    isEnabled = false
                    supportBackgroundTintList =
                        ColorStateList.valueOf(resourcesManager.getColor(R.color.fabDisabled))
                }
            }

            binding.addButton.setOnClickListener {
                basketInteractions.addProductToBasket(product?.code)
            }

            binding.executePendingBindings()
        }
    }


    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(
                oldProduct: Product,
                newProduct: Product
            ) = oldProduct.code == newProduct.code

            override fun areContentsTheSame(
                oldProduct: Product,
                newProduct: Product
            ) = oldProduct == newProduct
        }
    }


}