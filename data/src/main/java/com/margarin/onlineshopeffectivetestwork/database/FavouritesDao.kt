package com.margarin.onlineshopeffectivetestwork.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.margarin.onlineshopeffectivetestwork.database.model.ProductDb
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavourite(product: ProductDb)

    @Query("SELECT * FROM favourites")
    fun getFavouriteProducts(): Flow<List<ProductDb>>

    @Query("SELECT EXISTS (SELECT * FROM favourites WHERE id=:itemId LIMIT 1)")
    fun observeIsFavourite(itemId: String) : Flow<Boolean>

    @Query("DELETE FROM favourites WHERE id=:itemId")
    suspend fun removeFromFavourites(itemId: String)

    @Query("DELETE FROM favourites")
    suspend fun removeAllFromFavourites()

}