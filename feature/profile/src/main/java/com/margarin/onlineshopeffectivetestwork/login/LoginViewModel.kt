package com.margarin.onlineshopeffectivetestwork.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.margarin.onlineshopeffectivetestwork.model.Profile
import com.margarin.onlineshopeffectivetestwork.usecase.profile.AddProfileUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val addProfileUseCase: AddProfileUseCase
): ViewModel()  {

    fun addProfile(firstName: String, lastName: String, phoneNumber: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val profile = Profile(
                firstName = firstName,
                lastName = lastName,
                phoneNumber = phoneNumber
            )
            addProfileUseCase(profile)
        }
    }
}