package com.margarin.onlineshopeffectivetestwork.di

import com.margarin.onlineshopeffectivetestwork.Feature
import com.margarin.onlineshopeffectivetestwork.catalog.CatalogFragment
import com.margarin.onlineshopeffectivetestwork.details.DetailsFragment
import com.margarin.onlineshopeffectivetestwork.favourites.FavouritesFragment


@Feature
interface ProductComponent {

    fun injectCatalogFragment(catalogFragment: CatalogFragment)
    fun injectDetailsFragment(detailsFragment: DetailsFragment)
    fun injectFavouritesFragment(favouritesFragment: FavouritesFragment)

}
