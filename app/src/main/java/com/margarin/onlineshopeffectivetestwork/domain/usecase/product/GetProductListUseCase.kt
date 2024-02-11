package com.margarin.onlineshopeffectivetestwork.domain.usecase.product

import com.margarin.onlineshopeffectivetestwork.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductListUseCase @Inject constructor(
    private val searchRepository: ProductRepository
) {
    suspend fun getProductList() = searchRepository.getProductList()
}