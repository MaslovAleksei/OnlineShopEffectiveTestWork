package com.margarin.onlineshopeffectivetestwork.mapper

import com.margarin.onlineshopeffectivetestwork.database.model.ProfileDb
import com.margarin.onlineshopeffectivetestwork.model.Profile

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