package com.margarin.onlineshopeffectivetestwork.presentation.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.margarin.onlineshopeffectivetestwork.domain.model.Product
import com.margarin.onlineshopeffectivetestwork.domain.usecase.favourite.ChangeFavouriteStateUseCase
import com.margarin.onlineshopeffectivetestwork.domain.usecase.favourite.ObserveFavouriteStateUseCase
import com.margarin.onlineshopeffectivetestwork.domain.usecase.product.DownloadProductListUseCase
import com.margarin.onlineshopeffectivetestwork.domain.usecase.product.GetProductListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import javax.inject.Inject

class CatalogViewModel @Inject constructor(
    private val getProductListUseCase: GetProductListUseCase,
    private val downloadProductListUseCase: DownloadProductListUseCase,
    private val changeFavouriteStateUseCase: ChangeFavouriteStateUseCase,
    private val observeFavouriteStateUseCase: ObserveFavouriteStateUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<CatalogState>(CatalogState.Initial)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            downloadProductListUseCase()
        }
    }

    fun sendEvent(event: CatalogEvent) {
        when (event) {
            CatalogEvent.GetProductList -> {
                viewModelScope.launch {
                    getProductListUseCase.getProductList()

                        .onStart { _state.value = CatalogState.Loading }
                        .onEach { _state.value = CatalogState.Content(it) }
                        .filter { it.isEmpty() }
                        .collect { _state.value = CatalogState.Error }
                }
            }

            CatalogEvent.SortListByAsc -> {
                val currentState = _state.value
                if (currentState is CatalogState.Content) {
                    val oldList = currentState.products
                    val newList = oldList.sortedBy { it.priceWithDiscount }
                    _state.value = currentState.copy(newList)
                }
            }

            CatalogEvent.SortListByDesc -> {
                val currentState = _state.value
                if (currentState is CatalogState.Content) {
                    val oldList = currentState.products
                    val newList = oldList.sortedByDescending { it.priceWithDiscount }
                    _state.value = currentState.copy(newList)
                }
            }

            CatalogEvent.SortListByRating -> {
                val currentState = _state.value
                if (currentState is CatalogState.Content) {
                    val oldList = currentState.products
                    val newList = oldList.sortedByDescending { it.rating }
                    _state.value = currentState.copy(newList)
                }
            }

            CatalogEvent.FilterByBody -> {
                viewModelScope.launch {
                    getProductListUseCase.getProductList()
                        .onStart { _state.value = CatalogState.Loading }
                        .transform {
                            val filteredList = mutableListOf<Product>()
                            it.forEach { product ->
                                if (product.tags.contains("body")) {
                                    filteredList.add(product)
                                }
                            }
                            emit(filteredList)
                        }
                        .onEach { _state.value = CatalogState.Content(it) }
                        .filter { it.isEmpty() }
                        .collect { _state.value = CatalogState.Error }
                }
            }

            CatalogEvent.FilterByFace -> {
                viewModelScope.launch {
                    getProductListUseCase.getProductList()
                        .onStart { _state.value = CatalogState.Loading }
                        .transform {
                            val filteredList = mutableListOf<Product>()
                            it.forEach { product ->
                                if (product.tags.contains("face")) {
                                    filteredList.add(product)
                                }
                            }
                            emit(filteredList)
                        }
                        .onEach { _state.value = CatalogState.Content(it) }
                        .filter { it.isEmpty() }
                        .collect { _state.value = CatalogState.Error }
                }
            }

            CatalogEvent.FilterByMask -> {
                viewModelScope.launch {
                    getProductListUseCase.getProductList()
                        .onStart { _state.value = CatalogState.Loading }
                        .transform {
                            val filteredList = mutableListOf<Product>()
                            it.forEach { product ->
                                if (product.tags.contains("mask")) {
                                    filteredList.add(product)
                                }
                            }
                            emit(filteredList)
                        }
                        .onEach { _state.value = CatalogState.Content(it) }
                        .filter { it.isEmpty() }
                        .collect { _state.value = CatalogState.Error }
                }
            }

            CatalogEvent.FilterBySuntan -> {
                viewModelScope.launch {
                    getProductListUseCase.getProductList()
                        .onStart { _state.value = CatalogState.Loading }
                        .transform {
                            val filteredList = mutableListOf<Product>()
                            it.forEach { product ->
                                if (product.tags.contains("suntan")) {
                                    filteredList.add(product)
                                }
                            }
                            emit(filteredList)
                        }
                        .onEach { _state.value = CatalogState.Content(it) }
                        .filter { it.isEmpty() }
                        .collect { _state.value = CatalogState.Error }
                }
            }
        }
    }
}