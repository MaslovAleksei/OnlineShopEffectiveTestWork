package com.margarin.onlineshopeffectivetestwork.di

import android.content.Context
import com.margarin.onlineshopeffectivetestwork.AppScope
import com.margarin.onlineshopeffectivetestwork.MainActivity
import com.margarin.onlineshopeffectivetestwork.ShopApp
import com.margarin.onlineshopeffectivetestwork.catalog.CatalogFragment
import com.margarin.onlineshopeffectivetestwork.details.DetailsFragment
import com.margarin.onlineshopeffectivetestwork.favourites.FavouritesFragment
import com.margarin.onlineshopeffectivetestwork.login.LoginFragment
import com.margarin.onlineshopeffectivetestwork.profile.ProfileFragment
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent: ProfileComponent, ProductComponent {

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