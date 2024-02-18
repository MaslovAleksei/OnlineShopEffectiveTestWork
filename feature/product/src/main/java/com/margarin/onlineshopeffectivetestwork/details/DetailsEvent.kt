package com.margarin.onlineshopeffectivetestwork.details

import com.margarin.onlineshopeffectivetestwork.model.Product

sealed class DetailsEvent {

    data class CheckFavouriteStatus(val productId: String): DetailsEvent()

    data class ChangeFavouriteStatus(val product: Product): DetailsEvent()
}