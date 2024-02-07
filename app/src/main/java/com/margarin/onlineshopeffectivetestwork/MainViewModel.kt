package com.margarin.onlineshopeffectivetestwork

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.margarin.onlineshopeffectivetestwork.domain.usecase.GetAuthStateFlowUseCase
import com.margarin.onlineshopeffectivetestwork.domain.usecase.product.DownloadProductListUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getAuthStateFlowUseCase: GetAuthStateFlowUseCase,
    private val downloadProductListUseCase: DownloadProductListUseCase
): ViewModel() {

    val authState = getAuthStateFlowUseCase()

    init {
        viewModelScope.launch {
            downloadProductListUseCase()
        }
    }
}