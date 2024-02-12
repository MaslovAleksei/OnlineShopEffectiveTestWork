package com.margarin.onlineshopeffectivetestwork.network.model

data class PriceDto(
    val price: String,
    val discount: Int,
    val priceWithDiscount: String,
    val unit: String
)
