package com.margarin.onlineshopeffectivetestwork.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.margarin.onlineshopeffectivetestwork.usecase.product.ChangeFavouriteStateUseCase
import com.margarin.onlineshopeffectivetestwork.usecase.product.CheckFavouriteStateUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val changeFavouriteStateUseCase: ChangeFavouriteStateUseCase,
    private val checkFavouriteStateUseCase: CheckFavouriteStateUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<DetailsState>(DetailsState.Initial)
    val state = _state.asStateFlow()

    internal fun sendEvent(event: DetailsEvent) {
        when (event) {

            is DetailsEvent.CheckFavouriteStatus -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _state.value = DetailsState.Details(checkFavouriteStateUseCase(event.productId))
                }
            }

            is DetailsEvent.ChangeFavouriteStatus -> {
                viewModelScope.launch(Dispatchers.IO) {
                    if (checkFavouriteStateUseCase(event.product.id)) {
                        changeFavouriteStateUseCase.removeFromFavourite(event.product.id)
                        _state.value = DetailsState.Details(isFavourite = false)
                    } else {
                        changeFavouriteStateUseCase.addToFavourite(event.product)
                        _state.value = DetailsState.Details(isFavourite = true)
                    }
                }
            }
        }
    }
}