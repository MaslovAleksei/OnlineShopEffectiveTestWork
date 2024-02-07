package com.margarin.onlineshopeffectivetestwork.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profiles")
data class ProfileDb(
    @PrimaryKey
    val id: Int,
    val firstName: String,
    val secondName: String,
    val phoneNumber: String
)
