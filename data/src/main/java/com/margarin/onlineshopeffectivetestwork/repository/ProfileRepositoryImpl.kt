package com.margarin.onlineshopeffectivetestwork.repository

import com.margarin.onlineshopeffectivetestwork.database.ProfileDao
import com.margarin.onlineshopeffectivetestwork.mapper.toDbModel
import com.margarin.onlineshopeffectivetestwork.mapper.toEntity
import com.margarin.onlineshopeffectivetestwork.model.AuthState
import com.margarin.onlineshopeffectivetestwork.model.Profile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileDao: ProfileDao
) : ProfileRepository {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private val authStateFlow = flow {
        profileDao.observeIsAuthorized().collect{
            val authState = if (it) AuthState.Authorized else AuthState.NotAuthorized
            emit(authState)
        }
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = AuthState.Initial
    )

    override fun getAuthStateFlow(): StateFlow<AuthState> = authStateFlow

    override suspend fun getProfile(): Profile = profileDao.getProfile().toEntity()

    override suspend fun addProfile(profile: Profile) {
        profileDao.addProfile(profile.toDbModel())
    }

    override suspend fun removeProfile() {
        profileDao.removeProfile()
    }
}