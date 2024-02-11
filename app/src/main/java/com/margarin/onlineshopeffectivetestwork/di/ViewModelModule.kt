package com.margarin.onlineshopeffectivetestwork.di

import androidx.lifecycle.ViewModel
import com.margarin.onlineshopeffectivetestwork.MainViewModel
import com.margarin.onlineshopeffectivetestwork.presentation.catalog.CatalogViewModel
import com.margarin.onlineshopeffectivetestwork.presentation.details.DetailsViewModel
import com.margarin.onlineshopeffectivetestwork.presentation.favourites.FavouritesViewModel
import com.margarin.onlineshopeffectivetestwork.presentation.login.LoginViewModel
import com.margarin.onlineshopeffectivetestwork.presentation.profile.ProfileViewModel
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
    @ViewModelKey(ProfileViewModel::class)
    fun bindProfileViewModel(viewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CatalogViewModel::class)
    fun bindCatalogViewModel(viewModel: CatalogViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavouritesViewModel::class)
    fun bindFavouriteViewModel(viewModel: FavouritesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    fun bindDetailsViewModel(viewModel: DetailsViewModel): ViewModel



}