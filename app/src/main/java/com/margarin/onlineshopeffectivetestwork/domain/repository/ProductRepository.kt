package com.margarin.onlineshopeffectivetestwork.domain.repository

import com.margarin.onlineshopeffectivetestwork.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getProductList(): Flow<List<Product>>

    suspend fun downloadProductList()

    suspend fun getProductItem(productId: String): Product
}