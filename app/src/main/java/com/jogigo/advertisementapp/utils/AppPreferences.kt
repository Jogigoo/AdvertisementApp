package com.jogigo.advertisementapp.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jogigo.advertisementapp.data.models.Property
import com.jogigo.advertisementapp.utils.Constants.Companion.ADVERTISEMENTS
import com.jogigo.advertisementapp.utils.Constants.Companion.APPLICATION_ID
import com.jogigo.advertisementapp.utils.Constants.Companion.FAVOURITES

class AppPreferences(context: Context) : BasePreferences(
    context, APPLICATION_ID
) {
    var properties: MutableList<Property>
        set(value) = prefs().set(ADVERTISEMENTS, Gson().toJson(value))
        get() = Gson().fromJson(
            prefs()[ADVERTISEMENTS, "[]"],
            object : TypeToken<MutableList<Property>>() {}.type
        )

    var favourites: MutableList<Property>
        set(value) = prefs().set(FAVOURITES, Gson().toJson(value))
        get() = Gson().fromJson(
            prefs()[FAVOURITES, "[]"],
            object : TypeToken<MutableList<Property>>() {}.type
        )
}