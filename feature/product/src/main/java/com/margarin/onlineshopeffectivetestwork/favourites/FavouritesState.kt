package com.margarin.onlineshopeffectivetestwork.favourites

import com.margarin.onlineshopeffectivetestwork.model.Product


sealed class FavouritesState {

    data class Favorites(val products: List<Product>) : FavouritesState()
    data object Brands: FavouritesState()
    data object NoItems : FavouritesState()
    data object Initial : FavouritesState()
}

