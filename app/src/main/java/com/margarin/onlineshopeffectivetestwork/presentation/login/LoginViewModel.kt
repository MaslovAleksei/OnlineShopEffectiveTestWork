package com.margarin.onlineshopeffectivetestwork.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.margarin.onlineshopeffectivetestwork.domain.model.Profile
import com.margarin.onlineshopeffectivetestwork.domain.usecase.profile.AddProfileUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val addProfileUseCase: AddProfileUseCase
): ViewModel()  {

    fun add(profile: Profile) {
        viewModelScope.launch(Dispatchers.IO) {
            addProfileUseCase(profile)
        }
    }
}