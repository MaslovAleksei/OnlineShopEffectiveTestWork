package com.margarin.onlineshopeffectivetestwork.profile

sealed class ProfileEvent {

    data object GetFavouriteList : ProfileEvent()

    data object RemoveProfileUseCase : ProfileEvent()

}