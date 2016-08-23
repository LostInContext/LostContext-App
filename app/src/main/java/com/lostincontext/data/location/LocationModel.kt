package com.lostincontext.data.location


import com.google.android.gms.maps.model.LatLng


data class LocationModel(var placeName: String,
                         var latitude: Double,
                         var longitude: Double) {

    fun getLatLng(): LatLng = LatLng(latitude, longitude)
}

