package com.margarin.onlineshopeffectivetestwork.domain.usecase.profile

import com.margarin.onlineshopeffectivetestwork.domain.repository.ProfileRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke() = repository.getProfile()
}