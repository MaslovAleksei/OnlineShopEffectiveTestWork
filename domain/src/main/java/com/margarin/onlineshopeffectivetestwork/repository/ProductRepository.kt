package com.margarin.onlineshopeffectivetestwork.repository

import com.margarin.onlineshopeffectivetestwork.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun getProductList(): Flow<List<Product>>

    fun getFavouriteProducts(): Flow<List<Product>>

    fun observeIsFavourite(productId: String): Flow<Boolean>

    suspend fun addToFavourite(product: Product)

    suspend fun removeFromFavourite(productId: String)

    suspend fun removeAllFromFavourites()
}