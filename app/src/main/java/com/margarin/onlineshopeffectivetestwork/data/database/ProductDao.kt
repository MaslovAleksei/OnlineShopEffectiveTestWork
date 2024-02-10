package com.margarin.onlineshopeffectivetestwork.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.margarin.onlineshopeffectivetestwork.data.database.model.ProductDb
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProductList(products: List<ProductDb>)

    @Query("SELECT * FROM products ORDER BY rating DESC")
    fun getProductList(): Flow<List<ProductDb>>

    @Query("SELECT * FROM products WHERE id=:itemId LIMIT 1")
    suspend fun getProductItem(itemId: String): ProductDb

}