package com.raul.androidapps.lanaapplication.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.raul.androidapps.lanaapplication.R
import com.raul.androidapps.lanaapplication.databinding.BindingComponent
import com.raul.androidapps.lanaapplication.databinding.ProductRowBinding
import com.raul.androidapps.lanaapplication.persistence.entities.ProductEntity


class ProductAdapter(
    private val bindingComponent: BindingComponent
) :
    PagedListAdapter<ProductEntity, ProductAdapter.ProductViewHolder>(DIFF_CALLBACK) {

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
        val product: ProductEntity? = getItem(position)

        holder.bindTo(product)
    }

    class ProductViewHolder(private val binding: ProductRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindTo(product: ProductEntity?) {
            binding.product = product
            binding.executePendingBindings()
        }
    }


    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<ProductEntity>() {
            override fun areItemsTheSame(
                oldProductEntity: ProductEntity,
                newProductEntity: ProductEntity
            ) = oldProductEntity.code == newProductEntity.code

            override fun areContentsTheSame(
                oldProductEntity: ProductEntity,
                newProductEntity: ProductEntity
            ) = oldProductEntity == newProductEntity
        }
    }


}