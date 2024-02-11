package com.margarin.onlineshopeffectivetestwork.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.margarin.onlineshopeffectivetestwork.data.database.model.ProfileDb
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {

    @Query("SELECT EXISTS (SELECT * FROM profiles WHERE id=0 LIMIT 1)")
    fun observeIsAuthorized() : Flow<Boolean>

    @Query("SELECT * FROM profiles WHERE id=0")
    fun getProfile() : ProfileDb

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProfile(profile: ProfileDb)

    @Query("DELETE FROM profiles")
    suspend fun removeProfile()

}