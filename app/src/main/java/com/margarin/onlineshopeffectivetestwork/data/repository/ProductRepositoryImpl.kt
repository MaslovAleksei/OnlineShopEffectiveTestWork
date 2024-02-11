package com.margarin.onlineshopeffectivetestwork.data.repository

import com.margarin.onlineshopeffectivetestwork.data.database.ProductDao
import com.margarin.onlineshopeffectivetestwork.data.mapper.toDbModel
import com.margarin.onlineshopeffectivetestwork.data.mapper.toEntities
import com.margarin.onlineshopeffectivetestwork.data.mapper.toEntity
import com.margarin.onlineshopeffectivetestwork.data.network.ApiService
import com.margarin.onlineshopeffectivetestwork.domain.model.Product
import com.margarin.onlineshopeffectivetestwork.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val productDao: ProductDao
) : ProductRepository {

    override fun getProductList(): Flow<List<Product>> =
        productDao.getProductList().map { it.toEntities() }

    override suspend fun downloadProductList() {
        try {
            val productsDto = apiService.getProductList()
            val productsDb = productsDto.items.map { it.toDbModel() }
            productDao.addProductList(productsDb)
        } catch (_: Exception) {
        }
    }

    override suspend fun getProductItem(productId: String) =
        productDao.getProductItem(productId).toEntity()

}