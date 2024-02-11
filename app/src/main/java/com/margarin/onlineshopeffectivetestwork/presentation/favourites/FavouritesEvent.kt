package com.margarin.onlineshopeffectivetestwork.presentation.favourites

import com.margarin.onlineshopeffectivetestwork.domain.model.Product

sealed class FavouritesEvent {

    data object GetFavouriteList : FavouritesEvent()

    data class ChangeFavouriteStatus(val product: Product): FavouritesEvent()

}