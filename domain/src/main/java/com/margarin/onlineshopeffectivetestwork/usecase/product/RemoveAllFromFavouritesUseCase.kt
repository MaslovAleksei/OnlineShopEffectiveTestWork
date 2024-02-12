package com.margarin.onlineshopeffectivetestwork.usecase.product

import com.margarin.onlineshopeffectivetestwork.repository.ProductRepository
import javax.inject.Inject

class RemoveAllFromFavouritesUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke() = repository.removeAllFromFavourites()
}