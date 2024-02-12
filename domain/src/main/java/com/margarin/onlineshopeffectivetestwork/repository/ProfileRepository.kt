package com.margarin.onlineshopeffectivetestwork.repository

import com.margarin.onlineshopeffectivetestwork.model.AuthState
import com.margarin.onlineshopeffectivetestwork.model.Profile
import kotlinx.coroutines.flow.StateFlow

interface ProfileRepository {

    fun getAuthStateFlow(): StateFlow<AuthState>

    suspend fun getProfile(): Profile

    suspend fun addProfile(profile: Profile)

    suspend fun removeProfile()
}