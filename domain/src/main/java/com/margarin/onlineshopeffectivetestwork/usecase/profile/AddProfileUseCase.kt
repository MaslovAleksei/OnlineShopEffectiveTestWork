package com.margarin.onlineshopeffectivetestwork.usecase.profile

import com.margarin.onlineshopeffectivetestwork.model.Profile
import com.margarin.onlineshopeffectivetestwork.repository.ProfileRepository
import javax.inject.Inject

class AddProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(profile: Profile) = repository.addProfile(profile)
}