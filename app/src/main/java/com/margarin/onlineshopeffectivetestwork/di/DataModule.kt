package com.margarin.onlineshopeffectivetestwork.di

import android.content.Context
import com.margarin.onlineshopeffectivetestwork.data.database.AppDatabase
import com.margarin.onlineshopeffectivetestwork.data.database.FavouritesDao
import com.margarin.onlineshopeffectivetestwork.data.database.ProductDao
import com.margarin.onlineshopeffectivetestwork.data.database.ProfileDao
import com.margarin.onlineshopeffectivetestwork.data.network.ApiFactory
import com.margarin.onlineshopeffectivetestwork.data.network.ApiService
import com.margarin.onlineshopeffectivetestwork.data.repository.FavouriteRepositoryImpl
import com.margarin.onlineshopeffectivetestwork.data.repository.ProductRepositoryImpl
import com.margarin.onlineshopeffectivetestwork.data.repository.ProfileRepositoryImpl
import com.margarin.onlineshopeffectivetestwork.domain.repository.FavouriteRepository
import com.margarin.onlineshopeffectivetestwork.domain.repository.ProductRepository
import com.margarin.onlineshopeffectivetestwork.domain.repository.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @AppScope
    @Binds
    fun bindProfileRepository(impl: ProfileRepositoryImpl): ProfileRepository

    @Binds
    @AppScope
    fun bindProductRepository(impl: ProductRepositoryImpl): ProductRepository

    @Binds
    @AppScope
    fun bindFavouriteRepository(impl: FavouriteRepositoryImpl): FavouriteRepository

    companion object {

        @AppScope
        @Provides
        fun provideAppDatabase(context: Context): AppDatabase {
            return AppDatabase.getInstance(context)
        }

        @Provides
        @AppScope
        fun provideProfileDao(context: Context): ProfileDao {
            return AppDatabase.getInstance(context).profileDao()
        }

        @Provides
        @AppScope
        fun provideProductDao(context: Context): ProductDao {
            return AppDatabase.getInstance(context).productDao()
        }

        @Provides
        @AppScope
        fun provideFavouriteDao(context: Context): FavouritesDao {
            return AppDatabase.getInstance(context).favouriteDao()
        }

        @Provides
        @AppScope
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }
    }
}