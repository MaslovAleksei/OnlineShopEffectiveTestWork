package com.margarin.onlineshopeffectivetestwork.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.margarin.onlineshopeffectivetestwork.domain.usecase.RemoveProfileUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val removeProfileUseCase: RemoveProfileUseCase
): ViewModel()  {

    fun remove() {
        viewModelScope.launch {
            removeProfileUseCase()
        }

    }

}