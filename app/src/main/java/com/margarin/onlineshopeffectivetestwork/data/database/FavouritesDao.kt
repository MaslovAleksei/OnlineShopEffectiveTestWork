package com.margarin.onlineshopeffectivetestwork.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.margarin.onlineshopeffectivetestwork.data.database.model.ProductDb
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavourite(product: ProductDb)

    @Query("SELECT * FROM products")
    fun getFavouriteProducts(): Flow<List<ProductDb>>

    @Query("SELECT EXISTS (SELECT * FROM products WHERE id=:itemId LIMIT 1)")
    fun observeIsFavourite(itemId: String) : Flow<Boolean>

    @Query("DELETE FROM products WHERE id=:itemId")
    suspend fun removeFromFavourites(itemId: String)

}