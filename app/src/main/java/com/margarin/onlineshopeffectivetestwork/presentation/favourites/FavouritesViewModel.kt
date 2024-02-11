package com.margarin.onlineshopeffectivetestwork.presentation.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.margarin.onlineshopeffectivetestwork.domain.model.Product
import com.margarin.onlineshopeffectivetestwork.domain.usecase.favourite.ChangeFavouriteStateUseCase
import com.margarin.onlineshopeffectivetestwork.domain.usecase.favourite.GetFavouriteProductsUseCase
import com.margarin.onlineshopeffectivetestwork.domain.usecase.favourite.ObserveFavouriteStateUseCase
import com.margarin.onlineshopeffectivetestwork.domain.usecase.product.DownloadProductListUseCase
import com.margarin.onlineshopeffectivetestwork.domain.usecase.product.GetProductListUseCase
import com.margarin.onlineshopeffectivetestwork.presentation.catalog.CatalogState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.transform
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
                viewModelScope.launch {
                    getFavouriteProductsUseCase()
                        .onEach { _state.value = FavouritesState.Content(it) }
                        .filter { it.isEmpty() }
                        .collect { _state.value = FavouritesState.NoData }
                }
            }

            is FavouritesEvent.ChangeFavouriteStatus -> {
                viewModelScope.launch {
                    var favouriteState: Boolean? = null
                    observeFavouriteStateUseCase(event.product.id).collect{ favouriteState = it }
                    if (favouriteState == true){
                        changeFavouriteStateUseCase.removeFromFavourite(event.product.id)
                    } else {
                        changeFavouriteStateUseCase.addToFavourite(event.product)
                    }
                }
            }
        }
    }
}