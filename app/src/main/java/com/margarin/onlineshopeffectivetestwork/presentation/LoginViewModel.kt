package com.margarin.onlineshopeffectivetestwork.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.margarin.onlineshopeffectivetestwork.domain.model.Profile
import com.margarin.onlineshopeffectivetestwork.domain.usecase.AddProfileUseCase
import com.margarin.onlineshopeffectivetestwork.domain.usecase.RemoveProfileUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val addProfileUseCase: AddProfileUseCase
): ViewModel()  {

    fun add(profile: Profile) {
        viewModelScope.launch {
            addProfileUseCase(profile)
        }

    }

}