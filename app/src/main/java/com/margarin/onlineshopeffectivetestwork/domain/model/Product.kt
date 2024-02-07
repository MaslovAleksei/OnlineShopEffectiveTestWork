package com.margarin.onlineshopeffectivetestwork.domain.model

data class Product(
    val id: String,
    val title: String,
    val subtitle: String,
    val price: String,
    val discount: Int,
    val priceWithDiscount: String,
    val unit: String,
    val count: Int,
    val rating: Float,
    val tags: List<String>,
    val available: Int,
    val description: String,
    val info: List<Pair<String, String>>,
    val ingredients: String
)