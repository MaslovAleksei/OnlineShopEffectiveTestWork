package com.margarin.onlineshopeffectivetestwork.di

import android.content.Context
import com.margarin.onlineshopeffectivetestwork.MainActivity
import com.margarin.onlineshopeffectivetestwork.ShopApp
import com.margarin.onlineshopeffectivetestwork.presentation.catalog.CatalogFragment
import com.margarin.onlineshopeffectivetestwork.presentation.details.DetailsFragment
import com.margarin.onlineshopeffectivetestwork.presentation.favourites.FavouritesFragment
import com.margarin.onlineshopeffectivetestwork.presentation.login.LoginFragment
import com.margarin.onlineshopeffectivetestwork.presentation.profile.ProfileFragment
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    fun inject(application: ShopApp)

    fun inject(activity: MainActivity)

    fun inject(fragment: LoginFragment)
    fun inject(fragment: ProfileFragment)
    fun inject(fragment: CatalogFragment)
    fun inject(fragment: FavouritesFragment)
    fun inject(fragment: DetailsFragment)


    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance context: Context
        ): AppComponent
    }
}