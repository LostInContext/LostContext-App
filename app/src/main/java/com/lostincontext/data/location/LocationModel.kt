package com.lostincontext.data.location


import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.android.gms.maps.model.LatLng

@JsonIgnoreProperties(ignoreUnknown = true)
data class LocationModel(var placeName: String,
                         var latitude: Double,
                         var longitude: Double) {
    @JsonIgnore
    fun getLatLng(): LatLng = LatLng(latitude, longitude)
}

