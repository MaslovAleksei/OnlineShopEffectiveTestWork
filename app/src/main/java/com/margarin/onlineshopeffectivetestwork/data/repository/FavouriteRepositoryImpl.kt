package com.margarin.onlineshopeffectivetestwork.data.repository

import com.margarin.onlineshopeffectivetestwork.data.database.FavouritesDao
import com.margarin.onlineshopeffectivetestwork.data.mapper.toDbModel
import com.margarin.onlineshopeffectivetestwork.data.mapper.toEntities
import com.margarin.onlineshopeffectivetestwork.domain.model.Product
import com.margarin.onlineshopeffectivetestwork.domain.repository.FavouriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavouriteRepositoryImpl @Inject constructor(
    private val favouriteDao: FavouritesDao
) : FavouriteRepository {

    override fun getFavouriteProducts(): Flow<List<Product>> = favouriteDao.getFavouriteProducts()
        .map { it.toEntities() }

    override fun observeIsFavourite(productId: String): Flow<Boolean> =
        favouriteDao.observeIsFavourite(productId)

    override suspend fun addToFavourite(product: Product) {
        favouriteDao.addToFavourite(product.toDbModel())
    }

    override suspend fun removeFromFavourite(productId: String) {
        favouriteDao.removeFromFavourites(productId)
    }
}