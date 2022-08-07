package com.example.posapp.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters


@TypeConverters
class MealTypeConverter {

//    Insert inside the table
    @TypeConverter
    fun fromAnyToString(attribute: Any?): String {
        if (attribute == null)
            return ""
        return attribute as String
    }

//    Retrieve data from db
    @TypeConverter
    fun fromStringToAny(attribute: String?): Any {
        if (attribute == null)
            return ""
        return attribute
    }
}