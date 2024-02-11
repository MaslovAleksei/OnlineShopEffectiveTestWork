package com.margarin.onlineshopeffectivetestwork.domain.repository

import com.margarin.onlineshopeffectivetestwork.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface FavouriteRepository {

    fun getFavouriteProducts(): Flow<List<Product>>

    fun observeIsFavourite(productId: String): Flow<Boolean>

    suspend fun addToFavourite(product: Product)

    suspend fun removeFromFavourite(productId: String)

}