package com.margarin.onlineshopeffectivetestwork.data.mapper

import com.margarin.onlineshopeffectivetestwork.data.database.model.ProductDb
import com.margarin.onlineshopeffectivetestwork.data.network.model.ProductDto
import com.margarin.onlineshopeffectivetestwork.domain.model.Product

fun ProductDto.toDbModel(): ProductDb {
    val infoList = mutableListOf<Pair<String, String>>()
    info.forEach{
        val pairInfo = Pair(it.title, it.value)
        infoList.add(pairInfo)
    }
    return ProductDb(
        id = id,
        title = title,
        subtitle = subtitle,
        price = price.price,
        discount = price.discount,
        priceWithDiscount = price.priceWithDiscount,
        unit = price.unit,
        count = feedback.count,
        rating = feedback.rating,
        tags = tags,
        available = available,
        description = description,
        info = infoList,
        ingredients = ingredients
    )
}

fun ProductDb.toEntity() = Product(
    id = id,
    title = title,
    subtitle = subtitle,
    price = price,
    discount = discount,
    priceWithDiscount = priceWithDiscount,
    unit = unit,
    count = count,
    rating = rating,
    tags = tags,
    available = available,
    description = description,
    info = info,
    ingredients = ingredients
)