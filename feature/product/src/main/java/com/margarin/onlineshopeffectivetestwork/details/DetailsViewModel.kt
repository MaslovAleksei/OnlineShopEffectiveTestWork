package com.margarin.onlineshopeffectivetestwork.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.margarin.onlineshopeffectivetestwork.usecase.product.ChangeFavouriteStateUseCase
import com.margarin.onlineshopeffectivetestwork.usecase.product.ObserveFavouriteStateUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val changeFavouriteStateUseCase: ChangeFavouriteStateUseCase,
    private val observeFavouriteStateUseCase: ObserveFavouriteStateUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<DetailsState>(DetailsState.Initial)
    val state = _state.asStateFlow()

    fun sendEvent(event: DetailsEvent) {
        when (event) {

            is DetailsEvent.ObserveFavouriteStatus -> {
                viewModelScope.launch(Dispatchers.IO) {
                    observeFavouriteStateUseCase(event.productId).collect {
                        _state.value = DetailsState.Details(it)
                    }
                }
            }

            is DetailsEvent.ChangeFavouriteStatus -> {
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