package com.margarin.onlineshopeffectivetestwork.di

import android.content.Context
import com.margarin.onlineshopeffectivetestwork.data.database.AppDatabase
import com.margarin.onlineshopeffectivetestwork.data.database.FavouritesDao
import com.margarin.onlineshopeffectivetestwork.data.database.ProfileDao
import com.margarin.onlineshopeffectivetestwork.data.network.ApiFactory
import com.margarin.onlineshopeffectivetestwork.data.network.ApiService
import com.margarin.onlineshopeffectivetestwork.data.repository.ProductRepositoryImpl
import com.margarin.onlineshopeffectivetestwork.data.repository.ProfileRepositoryImpl
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