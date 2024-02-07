package com.margarin.onlineshopeffectivetestwork

import android.app.Application
import com.margarin.onlineshopeffectivetestwork.di.DaggerAppComponent

class ShopApp: Application() {

    val appComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        appComponent.inject(this)
        super.onCreate()
    }
}