package com.margarin.onlineshopeffectivetestwork.profile

import com.margarin.onlineshopeffectivetestwork.model.Product
import com.margarin.onlineshopeffectivetestwork.model.Profile


sealed class ProfileState {

    data class Favorites(
        val profile: Profile,
        val favourites: List<Product> = mutableListOf()
    ) : ProfileState()
    data object Initial : ProfileState()
}

