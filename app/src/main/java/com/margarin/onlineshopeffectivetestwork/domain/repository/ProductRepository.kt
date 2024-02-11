package com.margarin.onlineshopeffectivetestwork.domain.repository

import com.margarin.onlineshopeffectivetestwork.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun getProductList(): Flow<List<Product>>
}