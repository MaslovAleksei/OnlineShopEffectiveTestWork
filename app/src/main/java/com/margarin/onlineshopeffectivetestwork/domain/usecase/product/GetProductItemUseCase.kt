package com.margarin.onlineshopeffectivetestwork.domain.usecase.product

import com.margarin.onlineshopeffectivetestwork.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductItemUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(itemId: String) = repository.getProductItem(itemId)
}