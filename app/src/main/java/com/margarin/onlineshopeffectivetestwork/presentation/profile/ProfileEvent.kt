package com.margarin.onlineshopeffectivetestwork.presentation.profile

sealed class ProfileEvent {

    data object GetFavouriteList : ProfileEvent()

    data object RemoveProfileUseCase : ProfileEvent()

}