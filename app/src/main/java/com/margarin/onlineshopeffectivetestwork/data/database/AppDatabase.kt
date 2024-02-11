package com.margarin.onlineshopeffectivetestwork.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.margarin.onlineshopeffectivetestwork.data.database.model.ProductDb
import com.margarin.onlineshopeffectivetestwork.data.database.model.ProfileDb

@Database(
    entities = [
        ProfileDb::class,
        ProductDb::class
    ], version = 1, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {

        private var db: AppDatabase? = null
        private const val DB_NAME = "main.db"
        private val LOCK = Any()

        fun getInstance(context: Context): AppDatabase {
            synchronized(LOCK) {
                db?.let { return it }
                val instance =
                    Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        DB_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                db = instance
                return instance
            }
        }
    }

    abstract fun profileDao(): ProfileDao
    abstract fun productDao(): ProductDao
    abstract fun favouriteDao(): FavouritesDao

}