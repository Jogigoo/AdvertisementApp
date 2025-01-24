package com.jogigo.advertisementapp.data.api

import com.jogigo.advertisementapp.data.models.Property
import com.jogigo.advertisementapp.data.models.PropertyDetail
import com.jogigo.advertisementapp.utils.Constants.Companion.ADVERTISEMENT_DETAIL
import com.jogigo.advertisementapp.utils.Constants.Companion.ADVERTISEMENT_LIST
import com.jogigo.advertisementapp.utils.Constants.Companion.API_URL
import retrofit2.Call
import retrofit2.http.GET

internal interface ApiService {
    @GET(API_URL + ADVERTISEMENT_LIST)
    fun getAdvertisements(): Call<List<Property>>

    @GET(API_URL + ADVERTISEMENT_DETAIL)
    fun getAdvertisementDetail(): Call<PropertyDetail>
}