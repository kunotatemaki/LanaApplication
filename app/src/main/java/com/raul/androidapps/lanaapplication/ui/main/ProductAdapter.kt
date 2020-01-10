package com.raul.androidapps.lanaapplication.ui.main

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


class ProductAdapter(
    private val bindingComponent: BindingComponent
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
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product: Product? = getItem(position)

        holder.bindTo(product)
    }

    class ProductViewHolder(private val binding: ProductRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindTo(product: Product?) {
            binding.product = product
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