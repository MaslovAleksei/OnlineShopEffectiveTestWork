package com.margarin.onlineshopeffectivetestwork.data.mapper

import com.margarin.onlineshopeffectivetestwork.R
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
        priceWithDiscount = price.priceWithDiscount.toInt(),
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
    ingredients = ingredients,
    imageResId = id.parseImageResId()
)

fun Product.toDbModel() = ProductDb(
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
    ingredients = ingredients,
)

fun List<ProductDb>.toEntities(): List<Product> = map { it.toEntity() }

private fun String.parseImageResId(): List<Int> {
    return when(this) {
        "cbf0c984-7c6c-4ada-82da-e29dc698bb50" -> mutableListOf(
            R.drawable.ic_vox,
            R.drawable.ic_eveline
        )

        "54a876a5-2205-48ba-9498-cfecff4baa6e" -> mutableListOf(
            R.drawable.ic_pieu,
            R.drawable.ic_esfolio
        )

        "75c84407-52e1-4cce-a73a-ff2d3ac031b3" -> mutableListOf(
            R.drawable.ic_eveline,
            R.drawable.ic_vox
        )

        "16f88865-ae74-4b7c-9d85-b68334bb97db" -> mutableListOf(
            R.drawable.ic_deco,
            R.drawable.ic_lp_care
        )

        "26f88856-ae74-4b7c-9d85-b68334bb97db" -> mutableListOf(
            R.drawable.ic_esfolio,
            R.drawable.ic_deco
        )

        "15f88865-ae74-4b7c-9d81-b78334bb97db" -> mutableListOf(
            R.drawable.ic_vox,
            R.drawable.ic_pieu
        )

        "88f88865-ae74-4b7c-9d81-b78334bb97db" -> mutableListOf(
            R.drawable.ic_lp_care,
            R.drawable.ic_deco
        )

        "55f58865-ae74-4b7c-9d81-b78334bb97db" -> mutableListOf(
            R.drawable.ic_pieu,
            R.drawable.ic_eveline
        )

        else -> mutableListOf()
    }
}