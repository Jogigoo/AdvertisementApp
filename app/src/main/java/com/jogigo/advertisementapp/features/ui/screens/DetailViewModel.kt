
package com.jogigo.advertisementapp.features.ui.screens

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jogigo.advertisementapp.R
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

    fun getPropertyDetail(context: Context) {
        if (context.isNetworkConnected()) {
            _loading.value = true

            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val call = getRetrofit(context)
                        .create(ApiService::class.java)
                        .getAdvertisementDetail()
                        .execute()

                    val response = call.body()

                    withContext(Dispatchers.Main) {
                        _loading.value = false
                        response?.let {
                            _propertyDetail.value = it
                        } ?: run {
                            _errorMessage.value = context.getString(R.string.error_server)
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        _loading.value = false
                        _errorMessage.value = context.getString(R.string.error_unknown)
                    }
                }
            }
        } else {
            _loading.value = false
            _errorMessage.value = context.getString(R.string.error_internet)
        }
    }
}
