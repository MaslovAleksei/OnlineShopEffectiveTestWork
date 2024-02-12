package com.margarin.onlineshopeffectivetestwork

import android.app.Application
import com.margarin.onlineshopeffectivetestwork.di.DaggerAppComponent
import com.margarin.onlineshopeffectivetestwork.di.ProductComponent
import com.margarin.onlineshopeffectivetestwork.di.ProductComponentProvider
import com.margarin.onlineshopeffectivetestwork.di.ProfileComponent
import com.margarin.onlineshopeffectivetestwork.di.ProfileComponentProvider

class ShopApp: Application(), ProfileComponentProvider, ProductComponentProvider {

    val appComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        appComponent.inject(this)
        super.onCreate()
    }

    override fun getProfileComponent(): ProfileComponent {
        return appComponent
    }

    override fun getProductComponent(): ProductComponent {
        return appComponent
    }
}