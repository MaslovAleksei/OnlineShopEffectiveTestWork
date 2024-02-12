package com.margarin.onlineshopeffectivetestwork.presentation.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.margarin.onlineshopeffectivetestwork.domain.model.Product
import com.margarin.onlineshopeffectivetestwork.domain.usecase.product.ChangeFavouriteStateUseCase
import com.margarin.onlineshopeffectivetestwork.domain.usecase.product.ObserveFavouriteStateUseCase
import com.margarin.onlineshopeffectivetestwork.domain.usecase.product.GetProductListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import javax.inject.Inject

class CatalogViewModel @Inject constructor(
    private val getProductListUseCase: GetProductListUseCase,
    private val changeFavouriteStateUseCase: ChangeFavouriteStateUseCase,
    private val observeFavouriteStateUseCase: ObserveFavouriteStateUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<CatalogState>(CatalogState.Initial)
    val state = _state.asStateFlow()

    fun sendEvent(event: CatalogEvent) {
        when (event) {
            CatalogEvent.GetProductList -> {
                viewModelScope.launch(Dispatchers.IO) {
                    getProductListUseCase.getProductList()

                        .onStart { _state.value = CatalogState.Loading }
                        .map { mapToProductsWithFavouriteState(it) }
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
                viewModelScope.launch(Dispatchers.IO) {
                    getProductListUseCase.getProductList()
                        .onStart { _state.value = CatalogState.Loading }
                        .map { mapToProductsWithFavouriteState(it) }
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
                viewModelScope.launch(Dispatchers.IO) {
                    getProductListUseCase.getProductList()
                        .onStart { _state.value = CatalogState.Loading }
                        .map { mapToProductsWithFavouriteState(it) }
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
                viewModelScope.launch(Dispatchers.IO) {
                    getProductListUseCase.getProductList()
                        .onStart { _state.value = CatalogState.Loading }
                        .map { mapToProductsWithFavouriteState(it) }
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
                viewModelScope.launch(Dispatchers.IO) {
                    getProductListUseCase.getProductList()
                        .onStart { _state.value = CatalogState.Loading }
                        .map { mapToProductsWithFavouriteState(it) }
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

            is CatalogEvent.ChangeFavouriteStatus -> {
                viewModelScope.launch(Dispatchers.IO) {
                    observeFavouriteStateUseCase(event.product.id).first {
                        if (it) {
                            changeFavouriteStateUseCase.removeFromFavourite(event.product.id)
                        } else {
                            changeFavouriteStateUseCase.addToFavourite(event.product)
                        }
                        true
                    }
                }
            }
        }
    }

    private suspend fun mapToProductsWithFavouriteState(products: List<Product>): List<Product> {
        val newList = mutableListOf<Product>()

        products.toList().forEach {
            var isFavourite = false
            observeFavouriteStateUseCase(it.id).first { boolean ->
                isFavourite = boolean
                true
            }
            val product = if (isFavourite) it.copy(isFavourite = true) else it
            newList.add(product)
        }

        return newList
    }
}