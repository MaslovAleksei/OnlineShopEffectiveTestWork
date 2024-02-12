package com.margarin.onlineshopeffectivetestwork.usecase.product

import com.margarin.onlineshopeffectivetestwork.repository.ProductRepository
import javax.inject.Inject

class GetProductListUseCase @Inject constructor(
    private val searchRepository: ProductRepository
) {
    suspend fun getProductList() = searchRepository.getProductList()
}