package com.margarin.onlineshopeffectivetestwork.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.margarin.onlineshopeffectivetestwork.domain.usecase.product.GetFavouriteProductsUseCase
import com.margarin.onlineshopeffectivetestwork.domain.usecase.product.RemoveAllFromFavouritesUseCase
import com.margarin.onlineshopeffectivetestwork.domain.usecase.profile.GetProfileUseCase
import com.margarin.onlineshopeffectivetestwork.domain.usecase.profile.RemoveProfileUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val removeProfileUseCase: RemoveProfileUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val getFavouriteProductsUseCase: GetFavouriteProductsUseCase,
    private val removeAllFromFavouritesUseCase: RemoveAllFromFavouritesUseCase
): ViewModel()  {

    private val _state = MutableStateFlow<ProfileState>(ProfileState.Initial)
    val state = _state.asStateFlow()


    fun sendEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.RemoveProfileUseCase -> {
                viewModelScope.launch(Dispatchers.IO) {
                    removeProfileUseCase()
                    removeAllFromFavouritesUseCase()
                }
            }

            ProfileEvent.GetFavouriteList -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val profile = getProfileUseCase()
                    getFavouriteProductsUseCase()
                        .collect{
                            _state.value = ProfileState.Favorites(profile, it)
                        }
                }
            }
        }
    }
}