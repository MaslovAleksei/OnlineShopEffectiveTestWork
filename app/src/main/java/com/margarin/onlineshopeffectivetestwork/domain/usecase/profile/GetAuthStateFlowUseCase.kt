package com.margarin.onlineshopeffectivetestwork.domain.usecase.profile

import com.margarin.onlineshopeffectivetestwork.domain.repository.ProfileRepository
import javax.inject.Inject

class GetAuthStateFlowUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    operator fun invoke() = repository.getAuthStateFlow()
}