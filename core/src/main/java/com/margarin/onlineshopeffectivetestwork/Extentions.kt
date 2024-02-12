package com.margarin.onlineshopeffectivetestwork

import androidx.fragment.app.Fragment


fun Fragment.replaceFragment(containerResId: Int, fragment: Fragment) {
    requireActivity().supportFragmentManager
        .beginTransaction()
        .replace(containerResId, fragment)
        .addToBackStack(null)
        .commit()
}

@Suppress("UNCHECKED_CAST")
fun Fragment.replaceFragmentInOtherModule(containerResId: Int, className: String) {
    requireActivity().supportFragmentManager
        .beginTransaction()
        .replace(containerResId, Class.forName(className) as Class<out Fragment>, null)
        .addToBackStack(null)
        .commit()
}