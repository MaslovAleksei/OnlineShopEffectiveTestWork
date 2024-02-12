package com.margarin.onlineshopeffectivetestwork.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.margarin.onlineshopeffectivetestwork.R

fun AppCompatActivity.replaceFragment(fragment: Fragment) {
    supportFragmentManager.popBackStack()
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

@Suppress("UNCHECKED_CAST")
fun Fragment.replaceFragmentInOtherModule(className: String) {
    requireActivity().supportFragmentManager
        .beginTransaction()
        .replace(R.id.main_container, Class.forName(className) as Class<out Fragment>, null)
        .addToBackStack(null)
        .commit()
}