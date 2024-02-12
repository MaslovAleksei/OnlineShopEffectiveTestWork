package com.margarin.onlineshopeffectivetestwork.favourites

import com.margarin.onlineshopeffectivetestwork.model.Product

sealed class FavouritesEvent {

    data object GetFavouriteList : FavouritesEvent()

    data class ChangeFavouriteStatus(val product: Product): FavouritesEvent()

}