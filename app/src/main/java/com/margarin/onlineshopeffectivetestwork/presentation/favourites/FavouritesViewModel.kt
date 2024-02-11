package com.margarin.onlineshopeffectivetestwork.presentation.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.margarin.onlineshopeffectivetestwork.domain.usecase.favourite.ChangeFavouriteStateUseCase
import com.margarin.onlineshopeffectivetestwork.domain.usecase.favourite.GetFavouriteProductsUseCase
import com.margarin.onlineshopeffectivetestwork.domain.usecase.favourite.ObserveFavouriteStateUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouritesViewModel @Inject constructor(
    private val getFavouriteProductsUseCase: GetFavouriteProductsUseCase,
    private val changeFavouriteStateUseCase: ChangeFavouriteStateUseCase,
    private val observeFavouriteStateUseCase: ObserveFavouriteStateUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<FavouritesState>(FavouritesState.Initial)
    val state = _state.asStateFlow()

    fun sendEvent(event: FavouritesEvent) {
        when (event) {
            FavouritesEvent.GetFavouriteList -> {
                viewModelScope.launch(Dispatchers.IO) {
                    getFavouriteProductsUseCase()
                        .onEach { _state.value = FavouritesState.Content(it) }
                        .filter { it.isEmpty() }
                        .collect { _state.value = FavouritesState.NoItems }
                }
            }

            is FavouritesEvent.ChangeFavouriteStatus -> {
                viewModelScope.launch(Dispatchers.IO) {
                    observeFavouriteStateUseCase(event.product.id).first {
                        if (it) {
                            changeFavouriteStateUseCase.removeFromFavourite(event.product.id)
                        } else {
                            changeFavouriteStateUseCase.addToFavourite(event.product)
                        }
                        true
                    }
                }
            }
        }
    }
}