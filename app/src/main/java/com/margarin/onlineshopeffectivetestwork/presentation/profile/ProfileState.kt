package com.margarin.onlineshopeffectivetestwork.presentation.profile

import com.margarin.onlineshopeffectivetestwork.domain.model.Product
import com.margarin.onlineshopeffectivetestwork.domain.model.Profile


sealed class ProfileState {

    data class Favorites(
        val profile: Profile,
        val favourites: List<Product> = mutableListOf()
    ) : ProfileState()
    data object Initial : ProfileState()
}

