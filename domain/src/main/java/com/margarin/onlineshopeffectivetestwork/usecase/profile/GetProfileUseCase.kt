package com.margarin.onlineshopeffectivetestwork.usecase.profile

import com.margarin.onlineshopeffectivetestwork.repository.ProfileRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke() = repository.getProfile()
}