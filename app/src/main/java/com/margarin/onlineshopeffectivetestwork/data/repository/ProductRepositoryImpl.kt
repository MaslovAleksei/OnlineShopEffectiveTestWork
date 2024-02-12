package com.margarin.onlineshopeffectivetestwork.data.repository

import com.margarin.onlineshopeffectivetestwork.data.database.FavouritesDao
import com.margarin.onlineshopeffectivetestwork.data.mapper.toDbModel
import com.margarin.onlineshopeffectivetestwork.data.mapper.toEntities
import com.margarin.onlineshopeffectivetestwork.data.mapper.toEntity
import com.margarin.onlineshopeffectivetestwork.data.network.ApiService
import com.margarin.onlineshopeffectivetestwork.data.network.model.ProductsDto
import com.margarin.onlineshopeffectivetestwork.domain.model.Product
import com.margarin.onlineshopeffectivetestwork.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val favouriteDao: FavouritesDao
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

    override suspend fun removeAllFromFavourites() {
        favouriteDao.removeAllFromFavourites()
    }
}