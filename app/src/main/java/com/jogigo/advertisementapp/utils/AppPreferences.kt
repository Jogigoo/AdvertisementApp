package com.jogigo.advertisementapp.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jogigo.advertisementapp.data.models.Property
import com.jogigo.advertisementapp.utils.Constants.Companion.ADVERTISEMENTS
import com.jogigo.advertisementapp.utils.Constants.Companion.APPLICATION_ID
import com.jogigo.advertisementapp.utils.Constants.Companion.FAVOURITES
import com.jogigo.advertisementapp.utils.Extensions.Companion.getCurrentDateTime

class AppPreferences(context: Context) : BasePreferences(
    context, APPLICATION_ID
) {
    var properties: MutableList<Property>
        set(value) = prefs().set(ADVERTISEMENTS, Gson().toJson(value))
        get() = Gson().fromJson(
            prefs()[ADVERTISEMENTS, "[]"],
            object : TypeToken<MutableList<Property>>() {}.type
        )

    var favourites: MutableMap<String, String>
        get() {
            val json = prefs().getString(FAVOURITES, "{}")
            return try {
                Gson().fromJson(
                    json,
                    object : TypeToken<MutableMap<String, String>>() {}.type
                ) ?: mutableMapOf()
            } catch (e: Exception) {
                mutableMapOf()
            }
        }
        set(value) {
            val json = Gson().toJson(value)
            prefs().edit().putString(FAVOURITES, json).apply()
        }

    fun addFavourite(property: Property) {
        val favourites = this.favourites
        val currentDate = getCurrentDateTime()
        favourites[property.propertyCode] = currentDate
        this.favourites = favourites
    }

    fun removeFavourite(property: Property) {
        val favourites = this.favourites
        favourites.remove(property.propertyCode)
        this.favourites = favourites
    }
    fun getFavouriteDate(property: Property): String? {
        return this.favourites[property.propertyCode]
    }
}