package com.margarin.onlineshopeffectivetestwork.presentation.details


sealed class DetailsState {

    data class Details(val isFavourite: Boolean) : DetailsState()
    data object Initial : DetailsState()
}

