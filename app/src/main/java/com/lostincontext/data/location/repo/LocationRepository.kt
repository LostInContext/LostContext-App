package com.lostincontext.data.location.repo


import android.content.SharedPreferences
import android.util.Log

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.android.gms.maps.model.LatLng
import com.lostincontext.data.location.LocationModel

import java.io.IOException

import javax.inject.Inject
import javax.inject.Named

class LocationRepository
@Inject
constructor(@Named("location") private val preferences: SharedPreferences,
            private val objectMapper: ObjectMapper) {


    interface LocationCallback {

        fun onLocationFetched(locationModel: LocationModel)

        fun onLocationLoadFailed(name: String)
    }

    fun saveLocation(name: String, latLng: LatLng) {
        val model = LocationModel(name, latLng.latitude, latLng.longitude)
        try {
            saveToPrefs(name, serialize(model))
        } catch (e: JsonProcessingException) {
            Log.e(TAG, "save: ", e)
        }

    }

    fun getLocation(name: String, callback: LocationCallback) {
        val json = loadFromPrefs(name)
        if (json.isEmpty()) callback.onLocationLoadFailed(name)
        try {
            callback.onLocationFetched(deserialize(json))
        } catch (e: IOException) {
            e.printStackTrace()
            callback.onLocationLoadFailed(name)
        }

    }

    //region SharedPreferences editor

    private fun saveToPrefs(title: String, json: String) {
        val editor = preferences.edit()
        editor.putString(title, json)
        editor.apply()
        Log.i(TAG, "saving: " + json)
    }

    private fun loadFromPrefs(title: String): String {
        val data = preferences.getString(title, "")
        Log.i(TAG, "loading: " + data!!)
        return data
    }

    //endregion


    //region Serializer / deserializer

    @Throws(JsonProcessingException::class)
    private fun serialize(locationModel: LocationModel): String {
        return objectMapper.writeValueAsString(locationModel)

    }

    @Throws(IOException::class)
    private fun deserialize(json: String): LocationModel {
        return objectMapper.readerFor(LocationModel::class.java).readValue<LocationModel>(json)
    }

    companion object {

        private val TAG = LocationRepository::class.java.simpleName
    }
    //endregion

}
