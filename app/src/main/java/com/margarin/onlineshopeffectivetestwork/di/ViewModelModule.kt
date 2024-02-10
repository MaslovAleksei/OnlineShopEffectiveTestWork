package com.margarin.onlineshopeffectivetestwork.di

import androidx.lifecycle.ViewModel
import com.margarin.onlineshopeffectivetestwork.MainViewModel
import com.margarin.onlineshopeffectivetestwork.presentation.catalog.CatalogViewModel
import com.margarin.onlineshopeffectivetestwork.presentation.HomeViewModel
import com.margarin.onlineshopeffectivetestwork.presentation.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CatalogViewModel::class)
    fun bindCatalogViewModel(viewModel: CatalogViewModel): ViewModel



}