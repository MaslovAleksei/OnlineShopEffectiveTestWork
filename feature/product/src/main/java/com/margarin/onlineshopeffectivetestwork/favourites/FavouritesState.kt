package com.margarin.onlineshopeffectivetestwork.favourites

import com.margarin.onlineshopeffectivetestwork.model.Product


sealed class FavouritesState {

    data class Content(val products: List<Product>) : FavouritesState()
    data object NoItems : FavouritesState()
    data object Initial : FavouritesState()
}

