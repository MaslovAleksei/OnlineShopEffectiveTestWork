package com.margarin.onlineshopeffectivetestwork

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.margarin.onlineshopeffectivetestwork.domain.usecase.GetAuthStateFlowUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getAuthStateFlowUseCase: GetAuthStateFlowUseCase
): ViewModel() {

    val authState = getAuthStateFlowUseCase()

    private val _justNowLogged = MutableLiveData<Boolean>()
    val justNowLogged: LiveData<Boolean>
        get() = _justNowLogged

    init {
        _justNowLogged.value = false
    }

    fun changeJustNowLoggedState(boolean: Boolean) {
        _justNowLogged.value = boolean
    }
}