package com.margarin.onlineshopeffectivetestwork.domain.usecase.product

import com.margarin.onlineshopeffectivetestwork.domain.repository.ProductRepository
import javax.inject.Inject

class ObserveFavouriteStateUseCase  @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(productId: String) = repository.observeIsFavourite(productId)
}