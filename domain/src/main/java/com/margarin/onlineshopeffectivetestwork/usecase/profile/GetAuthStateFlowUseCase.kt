package com.margarin.onlineshopeffectivetestwork.usecase.profile

import com.margarin.onlineshopeffectivetestwork.repository.ProfileRepository
import javax.inject.Inject

class GetAuthStateFlowUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    operator fun invoke() = repository.getAuthStateFlow()
}