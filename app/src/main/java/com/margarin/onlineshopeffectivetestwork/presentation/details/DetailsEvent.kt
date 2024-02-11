package com.margarin.onlineshopeffectivetestwork.presentation.details

import com.margarin.onlineshopeffectivetestwork.domain.model.Product

sealed class DetailsEvent {

    data class ObserveFavouriteStatus(val productId: String): DetailsEvent()

    data class ChangeFavouriteStatus(val product: Product): DetailsEvent()

}