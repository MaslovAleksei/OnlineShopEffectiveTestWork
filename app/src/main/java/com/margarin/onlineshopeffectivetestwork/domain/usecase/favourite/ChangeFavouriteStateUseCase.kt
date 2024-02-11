package com.margarin.onlineshopeffectivetestwork.domain.usecase.favourite

import com.margarin.onlineshopeffectivetestwork.domain.model.Product
import com.margarin.onlineshopeffectivetestwork.domain.repository.FavouriteRepository
import javax.inject.Inject

class ChangeFavouriteStateUseCase  @Inject constructor(
    private val repository: FavouriteRepository
) {
    suspend fun addToFavourite(product: Product) = repository.addToFavourite(product)

    suspend fun removeFromFavourite(productId: String) = repository.removeFromFavourite(productId)
}