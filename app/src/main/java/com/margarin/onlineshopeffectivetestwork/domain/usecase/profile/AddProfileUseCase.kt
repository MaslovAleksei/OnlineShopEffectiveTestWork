package com.margarin.onlineshopeffectivetestwork.domain.usecase.profile

import com.margarin.onlineshopeffectivetestwork.domain.model.Profile
import com.margarin.onlineshopeffectivetestwork.domain.repository.ProfileRepository
import javax.inject.Inject

class AddProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(profile: Profile) = repository.addProfile(profile)
}