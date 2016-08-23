package com.lostincontext.data.playlist


import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper

import java.io.IOException

class PlaylistPicker {


    var playlist: Playlist? = null

    constructor() {
    }

    constructor(playlist: Playlist) {
        this.playlist = playlist
    }

    //region Serializer / deserializer

    @Throws(JsonProcessingException::class)
    fun serialize(): String {
        return ObjectMapper().writeValueAsString(this)

    }

    companion object {

        @Throws(IOException::class)
        fun deserialize(json: String): PlaylistPicker {
            return ObjectMapper().readerFor(PlaylistPicker::class.java).readValue<PlaylistPicker>(
                    json)
        }
    }
    //endregion
}
