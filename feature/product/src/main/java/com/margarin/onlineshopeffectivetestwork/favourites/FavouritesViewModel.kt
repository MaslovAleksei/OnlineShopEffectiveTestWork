package com.margarin.onlineshopeffectivetestwork.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.margarin.onlineshopeffectivetestwork.usecase.product.ChangeFavouriteStateUseCase
import com.margarin.onlineshopeffectivetestwork.usecase.product.CheckFavouriteStateUseCase
import com.margarin.onlineshopeffectivetestwork.usecase.product.GetFavouriteProductsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouritesViewModel @Inject constructor(
    private val getFavouriteProductsUseCase: GetFavouriteProductsUseCase,
    private val changeFavouriteStateUseCase: ChangeFavouriteStateUseCase,
    private val checkFavouriteStateUseCase: CheckFavouriteStateUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<FavouritesState>(FavouritesState.Initial)
    val state = _state.asStateFlow()

    internal fun sendEvent(event: FavouritesEvent) {
        when (event) {
            FavouritesEvent.GetFavouriteList -> {
                viewModelScope.launch(Dispatchers.IO) {
                    getFavouriteProductsUseCase()
                        .onEach { _state.value = FavouritesState.Favorites(it) }
                        .filter { it.isEmpty() }
                        .collect { _state.value = FavouritesState.NoItems }
                }
            }

            is FavouritesEvent.ChangeFavouriteStatus -> {
                viewModelScope.launch(Dispatchers.IO) {
                    if (checkFavouriteStateUseCase(event.product.id)) {
                        changeFavouriteStateUseCase.removeFromFavourite(event.product.id)
                    } else {
                        changeFavouriteStateUseCase.addToFavourite(event.product)
                    }
                }
            }

            FavouritesEvent.GetBrandsList -> {
                _state.value = FavouritesState.Brands
            }
        }
    }
}