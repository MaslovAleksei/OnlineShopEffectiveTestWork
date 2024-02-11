package com.margarin.onlineshopeffectivetestwork.presentation.favourites

import com.margarin.onlineshopeffectivetestwork.domain.model.Product


sealed class FavouritesState {

    data class Content(val products: List<Product>) : FavouritesState()
    data object NoItems : FavouritesState()
    data object Initial : FavouritesState()
}

