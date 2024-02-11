package com.margarin.onlineshopeffectivetestwork.data.repository

import com.margarin.onlineshopeffectivetestwork.data.mapper.toEntity
import com.margarin.onlineshopeffectivetestwork.data.network.ApiService
import com.margarin.onlineshopeffectivetestwork.data.network.model.ProductsDto
import com.margarin.onlineshopeffectivetestwork.domain.model.Product
import com.margarin.onlineshopeffectivetestwork.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ProductRepository {

    override suspend fun getProductList(): Flow<List<Product>> {
        var productsDto: ProductsDto? = null
        try {
            productsDto = apiService.getProductList()
        } catch (_: Exception) { }
        val products = productsDto?.items?.map { it.toEntity() } ?: listOf()
        return flow {
            emit(products.sortedByDescending { it.rating })
        }
    }
}