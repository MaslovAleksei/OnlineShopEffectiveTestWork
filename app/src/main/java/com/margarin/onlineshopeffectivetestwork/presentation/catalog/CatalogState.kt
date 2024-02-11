package com.margarin.onlineshopeffectivetestwork.presentation.catalog

import com.margarin.onlineshopeffectivetestwork.domain.model.Product


sealed class CatalogState {

    data object Error : CatalogState()
    data object Loading : CatalogState()
    data object Initial : CatalogState()

    data class Content(val products: List<Product>) : CatalogState()// {
//
//        data class ProductItem(
//            val product: Product,
//            val isFavourite: Boolean
//        )
//    }


}

