package com.margarin.onlineshopeffectivetestwork.usecase.product

import com.margarin.onlineshopeffectivetestwork.repository.ProductRepository
import javax.inject.Inject

class CheckFavouriteStateUseCase  @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(productId: String) = repository.checkIsFavourite(productId)
}