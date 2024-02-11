package com.margarin.onlineshopeffectivetestwork.presentation.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.denzcoskun.imageslider.models.SlideModel
import com.margarin.onlineshopeffectivetestwork.databinding.ProductItemBinding
import com.margarin.onlineshopeffectivetestwork.domain.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class ProductAdapter @Inject constructor(
    //private val observeFavouriteStateUseCase: ObserveFavouriteStateUseCase
) : ListAdapter<Product, ProductHolder>(ProductDiffCallback()) {

    var onAddToFavouriteClick: ((Product) -> Unit)? = null
    var onProductItemClick: ((Product) -> Unit)? = null

    val scope = CoroutineScope(Dispatchers.IO)

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
            root.setOnClickListener { onProductItemClick?.invoke(item) }
            imageButtonAddFavourite.setOnClickListener { onAddToFavouriteClick?.invoke(item) }

            val imageList = ArrayList<SlideModel>()
            item.imageResId.forEach {
                imageList.add(SlideModel(imagePath = it))
            }
            itemImageSlider.setImageList(imageList)

//            scope.launch {
//                observeFavouriteStateUseCase(productId = item.id).collect {
//                    val iconLike = if (it) R.drawable.ic_like_filled else R.drawable.ic_like_border
//                    imageButtonAddFavourite.setImageResource(iconLike)
//                }
//            }
            tvOldPrice.paintFlags = tvOldPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            val oldPrice = "${item.price} ₽"
            tvOldPrice.text = oldPrice
            val newPrice = "${item.priceWithDiscount} ₽"
            tvNewPrice.text = newPrice
            val discount = "-${item.discount}%"
            tvDiscount.text = discount
            tvTitle.text = item.title
            tvSubtitle.text = item.subtitle
            tvRating.text = item.rating.toString()
            val feedbackCount = "(${item.count})"
            tvFeedbackCount.text = feedbackCount
        }
    }
}