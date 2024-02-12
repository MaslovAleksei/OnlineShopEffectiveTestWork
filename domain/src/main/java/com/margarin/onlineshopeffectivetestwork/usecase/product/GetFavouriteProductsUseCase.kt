package com.margarin.onlineshopeffectivetestwork.usecase.product

import com.margarin.onlineshopeffectivetestwork.repository.ProductRepository
import javax.inject.Inject

class GetFavouriteProductsUseCase  @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke() = repository.getFavouriteProducts()
}