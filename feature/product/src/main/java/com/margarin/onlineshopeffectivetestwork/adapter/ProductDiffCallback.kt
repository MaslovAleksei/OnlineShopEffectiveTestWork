package com.margarin.onlineshopeffectivetestwork.adapter

import androidx.recyclerview.widget.DiffUtil
import com.margarin.onlineshopeffectivetestwork.model.Product

class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}