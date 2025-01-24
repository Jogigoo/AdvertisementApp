
package com.jogigo.advertisementapp.features.ui.screens.detail

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jogigo.advertisementapp.R
import com.jogigo.advertisementapp.data.api.APIAdvertisement.Companion.getPropertyDetail
import com.jogigo.advertisementapp.data.api.ApiService
import com.jogigo.advertisementapp.data.api.WSBase.Companion.getRetrofit
import com.jogigo.advertisementapp.data.models.PropertyDetail
import com.jogigo.advertisementapp.utils.Extensions.Companion.isNetworkConnected

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel : ViewModel() {

    private val _propertyDetail = MutableLiveData<PropertyDetail?>()
    val propertyDetail: LiveData<PropertyDetail?> get() = _propertyDetail

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun loadPropertyDetail(context: Context) {
        _loading.value = true

        viewModelScope.launch {
            try {
                val response = getPropertyDetail(context)
                _loading.value = false
                _propertyDetail.value = response
            } catch (e: Exception) {
                _loading.value = false
                _errorMessage.value = e.message
            }
        }
    }
}
