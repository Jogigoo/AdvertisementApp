package com.jogigo.advertisementapp.data.models
import com.google.gson.annotations.SerializedName

data class PropertyDetail(
    @SerializedName("adid") val adid: Int,
    @SerializedName("price") val price: Double,
    @SerializedName("priceInfo") val priceInfo: PriceInfo,
    @SerializedName("operation") val operation: String,
    @SerializedName("propertyType") val propertyType: String,
    @SerializedName("extendedPropertyType") val extendedPropertyType: String,
    @SerializedName("homeType") val homeType: String,
    @SerializedName("state") val state: String,
    @SerializedName("multimedia") val multimedia: Multimedia,
    @SerializedName("propertyComment") val propertyComment: String,
    @SerializedName("ubication") val ubication: Ubication,
    @SerializedName("country") val country: String,
    @SerializedName("moreCharacteristics") val moreCharacteristics: MoreCharacteristics,
    @SerializedName("energyCertification") val energyCertification: EnergyCertification
) {
    data class PriceInfo(
        @SerializedName("amount") val amount: Double,
        @SerializedName("currencySuffix") val currencySuffix: String
    )

    data class Multimedia(
        @SerializedName("images") val images: List<Image>
    ) {
        data class Image(
            @SerializedName("url") val url: String,
            @SerializedName("tag") val tag: String,
            @SerializedName("localizedName") val localizedName: String,
            @SerializedName("multimediaId") val multimediaId: Long
        )
    }

    data class Ubication(
        @SerializedName("latitude") val latitude: Double,
        @SerializedName("longitude") val longitude: Double
    )

    data class MoreCharacteristics(
        @SerializedName("communityCosts") val communityCosts: Double,
        @SerializedName("roomNumber") val roomNumber: Int,
        @SerializedName("bathNumber") val bathNumber: Int,
        @SerializedName("exterior") val exterior: Boolean,
        @SerializedName("housingFurnitures") val housingFurnitures: String,
        @SerializedName("agencyIsABank") val agencyIsABank: Boolean,
        @SerializedName("energyCertificationType") val energyCertificationType: String,
        @SerializedName("flatLocation") val flatLocation: String,
        @SerializedName("modificationDate") val modificationDate: Long,
        @SerializedName("constructedArea") val constructedArea: Int,
        @SerializedName("lift") val lift: Boolean,
        @SerializedName("boxroom") val boxroom: Boolean,
        @SerializedName("isDuplex") val isDuplex: Boolean,
        @SerializedName("floor") val floor: String,
        @SerializedName("status") val status: String
    )

    data class EnergyCertification(
        @SerializedName("title") val title: String,
        @SerializedName("energyConsumption") val energyConsumption: EnergyType,
        @SerializedName("emissions") val emissions: EnergyType
    ) {
        data class EnergyType(
            @SerializedName("type") val type: String
        )
    }
}
