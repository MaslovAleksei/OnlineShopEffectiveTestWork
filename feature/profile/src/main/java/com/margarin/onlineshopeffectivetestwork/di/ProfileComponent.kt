package com.margarin.onlineshopeffectivetestwork.di

import com.margarin.onlineshopeffectivetestwork.Feature
import com.margarin.onlineshopeffectivetestwork.login.LoginFragment
import com.margarin.onlineshopeffectivetestwork.profile.ProfileFragment


@Feature
interface ProfileComponent {

    fun injectLoginFragment(loginFragment: LoginFragment)
    fun injectProfileFragment(profileFragment: ProfileFragment)

}
