package com.margarin.onlineshopeffectivetestwork.domain.repository

import com.margarin.onlineshopeffectivetestwork.domain.model.AuthState
import com.margarin.onlineshopeffectivetestwork.domain.model.Profile
import kotlinx.coroutines.flow.StateFlow

interface ProfileRepository {

    fun getAuthStateFlow(): StateFlow<AuthState>

    suspend fun getProfile(): Profile?

    suspend fun addProfile(profile: Profile)

    suspend fun removeProfile()
}