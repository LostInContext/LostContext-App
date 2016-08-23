package com.lostincontext.data.location


import com.google.android.gms.maps.model.LatLng


class LocationModel {

    var latitude: Double = 0.toDouble()
    var longitude: Double = 0.toDouble()
    var placeName: String? = null

    @Suppress("unused") constructor() {
    }

    constructor(placeName: String, latitude: Double, longitude: Double) {
        this.latitude = latitude
        this.longitude = longitude
        this.placeName = placeName
    }

    fun getLatLng(): LatLng = LatLng(latitude, longitude)
}

