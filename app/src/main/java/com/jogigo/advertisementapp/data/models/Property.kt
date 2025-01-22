package com.jogigo.advertisementapp.data.models

import com.google.gson.annotations.SerializedName

data class Property(
    @SerializedName("propertyCode") val propertyCode: String,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("floor") val floor: String,
    @SerializedName("price") val price: Double,
    @SerializedName("priceInfo") val priceInfo: PriceInfo,
    @SerializedName("propertyType") val propertyType: String,
    @SerializedName("operation") val operation: String,
    @SerializedName("size") val size: Double,
    @SerializedName("exterior") val exterior: Boolean,
    @SerializedName("rooms") val rooms: Int,
    @SerializedName("bathrooms") val bathrooms: Int,
    @SerializedName("address") val address: String,
    @SerializedName("province") val province: String,
    @SerializedName("municipality") val municipality: String,
    @SerializedName("district") val district: String,
    @SerializedName("country") val country: String,
    @SerializedName("neighborhood") val neighborhood: String,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("description") val description: String,
    @SerializedName("multimedia") val multimedia: Multimedia,
    @SerializedName("parkingSpace") val parkingSpace: ParkingSpace?,
    @SerializedName("features") val features: Features
)

data class PriceInfo(
    @SerializedName("price") val price: Price
)

data class Price(
    @SerializedName("amount") val amount: Double,
    @SerializedName("currencySuffix") val currencySuffix: String
)

data class Multimedia(
    @SerializedName("images") val images: List<Image>
)

data class Image(
    @SerializedName("url") val url: String,
    @SerializedName("tag") val tag: String
)

data class ParkingSpace(
    @SerializedName("hasParkingSpace") val hasParkingSpace: Boolean,
    @SerializedName("isParkingSpaceIncludedInPrice") val isParkingSpaceIncludedInPrice: Boolean
)

data class Features(
    @SerializedName("hasAirConditioning") val hasAirConditioning: Boolean,
    @SerializedName("hasBoxRoom") val hasBoxRoom: Boolean,
    @SerializedName("hasSwimmingPool") val hasSwimmingPool: Boolean? = null,
    @SerializedName("hasTerrace") val hasTerrace: Boolean? = null,
    @SerializedName("hasGarden") val hasGarden: Boolean? = null
)