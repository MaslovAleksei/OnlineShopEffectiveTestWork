package com.margarin.onlineshopeffectivetestwork.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.denzcoskun.imageslider.models.SlideModel
import com.margarin.onlineshopeffectivetestwork.databinding.ProductItemBinding
import com.margarin.onlineshopeffectivetestwork.domain.model.Product

class ProductAdapter: ListAdapter<Product, ProductHolder>(ProductDiffCallback()) {

    var onAddToFavouriteClick: ((Product) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {

        val binding = ProductItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            val imageList = ArrayList<SlideModel>()
            item.imageResId.forEach{
                imageList.add(SlideModel( imagePath = it))
            }
                itemImageSlider.setImageList(imageList)
            imageButtonAddFavourite.setOnClickListener { onAddToFavouriteClick?.invoke(item) }
            tvOldPrice.text = item.price
            tvNewPrice.text = item.priceWithDiscount.toString()
            tvDiscount.text = item.discount.toString()
            tvTitle.text = item.title
            tvSubtitle.text = item.subtitle
            tvRating.text = item.rating.toString()
            tvFeedbackCount.text = item.count.toString()


        }


    }
}