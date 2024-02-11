package com.margarin.onlineshopeffectivetestwork.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.margarin.onlineshopeffectivetestwork.domain.model.Product

class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}