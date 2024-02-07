package com.margarin.onlineshopeffectivetestwork.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.margarin.onlineshopeffectivetestwork.R

fun AppCompatActivity.replaceFragment(fragment: Fragment) {
    supportFragmentManager
        .beginTransaction()
        .replace(R.id.main_container, fragment)
        .commit()
}