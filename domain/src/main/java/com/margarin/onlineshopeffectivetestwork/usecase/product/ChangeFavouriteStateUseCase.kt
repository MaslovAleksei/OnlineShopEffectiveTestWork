package com.margarin.onlineshopeffectivetestwork.usecase.product

import com.margarin.onlineshopeffectivetestwork.model.Product
import com.margarin.onlineshopeffectivetestwork.repository.ProductRepository
import javax.inject.Inject

class ChangeFavouriteStateUseCase  @Inject constructor(
    private val repository: ProductRepository
) {
    suspend fun addToFavourite(product: Product) = repository.addToFavourite(product)

    suspend fun removeFromFavourite(productId: String) = repository.removeFromFavourite(productId)
}