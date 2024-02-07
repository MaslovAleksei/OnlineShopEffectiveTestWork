package com.margarin.onlineshopeffectivetestwork.domain.usecase

import com.margarin.onlineshopeffectivetestwork.domain.repository.ProfileRepository
import javax.inject.Inject

class RemoveProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke() = repository.removeProfile()
}