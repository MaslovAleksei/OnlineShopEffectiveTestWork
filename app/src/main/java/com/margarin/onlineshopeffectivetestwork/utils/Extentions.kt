package com.margarin.onlineshopeffectivetestwork.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.margarin.onlineshopeffectivetestwork.R
import com.margarin.onlineshopeffectivetestwork.presentation.DetailsFragment

fun AppCompatActivity.replaceFragment(fragment: Fragment) {
    supportFragmentManager
        .beginTransaction()
        .replace(R.id.main_container, fragment)
        .commit()
}

fun Fragment.replaceFragment(fragment: Fragment) {
    requireActivity().supportFragmentManager
        .beginTransaction()
        .replace(R.id.main_container, fragment)
        .addToBackStack(null)
        .commit()
}