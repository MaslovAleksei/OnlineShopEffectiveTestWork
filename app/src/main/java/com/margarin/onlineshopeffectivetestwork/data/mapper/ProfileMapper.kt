package com.margarin.onlineshopeffectivetestwork.data.mapper

import com.margarin.onlineshopeffectivetestwork.data.database.model.ProfileDb
import com.margarin.onlineshopeffectivetestwork.domain.model.Profile

fun Profile.toDbModel() = ProfileDb(
    id = 0,
    firstName = firstName,
    lastName = lastName,
    phoneNumber = phoneNumber
)

fun ProfileDb.toEntity() = Profile(
    firstName = firstName,
    lastName = lastName,
    phoneNumber = phoneNumber
)