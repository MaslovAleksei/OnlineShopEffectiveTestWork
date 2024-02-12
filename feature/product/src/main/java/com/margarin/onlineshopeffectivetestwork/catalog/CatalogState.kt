package com.margarin.onlineshopeffectivetestwork.catalog

import com.margarin.onlineshopeffectivetestwork.model.Product


sealed class CatalogState {

    data object Error : CatalogState()
    data object Loading : CatalogState()
    data object Initial : CatalogState()
    data class Content(val products: List<Product>) : CatalogState()
}

