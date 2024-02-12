package com.margarin.onlineshopeffectivetestwork.database

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun tagsToJsonString(tags: List<String>) = Gson().toJson(tags)

    @TypeConverter
    fun jsonStringToTags(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()

    @TypeConverter
    fun pairListToStoredString(pairList: List<Pair<String, String>>): String {
        return pairList.joinToString(separator = "~!!!!!~") { pair ->
            pair.first + "!~~~~~!" + pair.second
        }
    }

    @TypeConverter
    fun storedStringToPairList(value: String): List<Pair<String, String>> {
        return value.split("~!!!!!~").map {
            val vals = it.split("!~~~~~!")
            Pair(vals[0], vals[1])
        }
    }
}