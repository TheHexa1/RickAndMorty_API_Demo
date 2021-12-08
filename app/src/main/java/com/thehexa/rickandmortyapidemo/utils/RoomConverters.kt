package com.thehexa.rickandmortyapidemo.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.JsonObject

class RoomConverters {

    @TypeConverter
    fun fromStringToJsonObject(location : String?):JsonObject?{
        return Gson().fromJson(location, JsonObject::class.java)
    }

    @TypeConverter
    fun fromJsonObjectToString(location : JsonObject?):String?{
        return Gson().toJson(location)
    }
}