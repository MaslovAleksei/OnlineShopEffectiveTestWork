package com.margarin.onlineshopeffectivetestwork.domain.usecase.favourite

import com.margarin.onlineshopeffectivetestwork.domain.repository.FavouriteRepository
import javax.inject.Inject

class ObserveFavouriteStateUseCase  @Inject constructor(
    private val repository: FavouriteRepository
) {
    operator fun invoke(productId: String) = repository.observeIsFavourite(productId)
}