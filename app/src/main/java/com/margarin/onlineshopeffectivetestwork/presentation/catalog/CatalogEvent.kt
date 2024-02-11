package com.margarin.onlineshopeffectivetestwork.presentation.catalog

sealed class CatalogEvent {

    data object GetProductList : CatalogEvent()

    data object SortListByRating : CatalogEvent()

    data object SortListByDesc : CatalogEvent()

    data object SortListByAsc : CatalogEvent()

    data object FilterByFace : CatalogEvent()

    data object FilterByBody : CatalogEvent()

    data object FilterBySuntan : CatalogEvent()

    data object FilterByMask : CatalogEvent()
}