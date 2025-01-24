package com.jogigo.advertisementapp.features.ui.fragments

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jogigo.advertisementapp.data.api.APIAdvertisement.Companion.getProperties
import com.jogigo.advertisementapp.data.models.Property
import com.jogigo.advertisementapp.utils.AppPreferences
import com.jogigo.advertisementapp.utils.Extensions.Companion.getCurrentDateTime
import kotlinx.coroutines.launch

class HousingViewModel : ViewModel() {

    private val _properties = MutableLiveData<List<Property>>()
    val properties: LiveData<List<Property>> get() = _properties

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    private val _favourites = MutableLiveData<Map<String, String>>(mutableMapOf())
    val favourites: LiveData<Map<String, String>> get() = _favourites


    fun addFavourite(context: Context, property: Property) {
        val appPreferences = AppPreferences(context)
        appPreferences.addFavourite(property)
        property.favourite = true
        property.dateFavourite = getCurrentDateTime()
    }

    fun removeFavourite(context: Context, property: Property) {
        val appPreferences = AppPreferences(context)
        appPreferences.removeFavourite(property)
        property.favourite = false
        property.dateFavourite = ""
    }

    fun loadFavourites(context: Context) {
        val appPreferences = AppPreferences(context)
        _favourites.value = appPreferences.favourites
    }

    fun loadProperties(context: Context) {
        val appPreferences = AppPreferences(context)
        val currentTime = System.currentTimeMillis()
        if (_properties.value.isNullOrEmpty()) {
            _loading.value = true
            viewModelScope.launch {
                try {
                    val propertyList = getProperties(context)

                    val favouriteMap = appPreferences.favourites

                    propertyList.forEach { property ->
                        val favouriteDate = favouriteMap[property.propertyCode]

                        if (favouriteDate != null) {
                            property.favourite = true
                            property.dateFavourite = favouriteDate
                        } else {
                            // La propiedad no es favorita
                            property.favourite = false
                            property.dateFavourite = ""
                        }
                    }
                    appPreferences.properties = propertyList.toMutableList()
                    appPreferences.lastFetchTime = currentTime
                    _properties.value = propertyList
                    _error.value = null
                } catch (e: Exception) {
                    _error.value = e.message
                } finally {
                    _loading.value = false
                }
            }
        }
    }
}