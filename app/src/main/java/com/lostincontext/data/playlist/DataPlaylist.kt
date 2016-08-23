package com.lostincontext.data.playlist

import android.text.TextUtils
import android.util.Log
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.lostincontext.commons.images.DeezerImageUrlGenerator.DeezerImageType
import com.lostincontext.commons.images.DeezerImageUrlGenerator.TYPE_COVER
import com.lostincontext.commons.images.DeezerImageUrlGenerator.TYPE_PLAYLIST_CUSTOM_COVER
import java.io.IOException

@JsonIgnoreProperties(ignoreUnknown = true)
class DataPlaylist {

    var data: List<Playlist> = emptyList()

    var total: Int = 0

    var next: String? = null

    /**
     * Extract the fields we want from the public API
     */
    private class PlaylistDeserializer : JsonDeserializer<Playlist>() {


        private class DataHolder {
            var coverMd5: String = ""
            @DeezerImageType var coverType: Long = 0L
        }

        internal var data = DataHolder()

        @Throws(IOException::class)
        override fun deserialize(p: JsonParser,
                                 ctxt: DeserializationContext): Playlist {
            val oc = p.codec
            val node = oc.readTree<JsonNode>(p)
            extractCoverMd5(node.get("picture_small").textValue(), data)

            return Playlist(node.get("id").asInt(),
                            node.get("title").textValue(),
                            node.get("creator").get("name").textValue(),
                            data.coverMd5,
                            data.coverType)


        }

        /**
         * dirty hack around the fact that the API can't simply return the coverMd5
         */
        private fun extractCoverMd5(cover: String, data: DataHolder) {
            var beginning = cover.indexOf("/cover/") + 7
            if (beginning == 6) {
                beginning = cover.indexOf("/playlist/") + 10
                data.coverType = TYPE_PLAYLIST_CUSTOM_COVER
            } else {
                data.coverType = TYPE_COVER
            }
            val end = cover.indexOf("/", beginning)
            data.coverMd5 = TextUtils.substring(cover, beginning, end)
        }


    }

    companion object {

        private val TAG = DataPlaylist::class.java.simpleName


        val playlists: List<Playlist>
            get() = deserialize(PlaylistJSON.JSON)

        fun deserialize(data: String): List<Playlist> {
            val mapper = jacksonObjectMapper()

            val simpleModule = SimpleModule()
            val customDeserializer = PlaylistDeserializer()
            simpleModule.addDeserializer(Playlist::class.java, customDeserializer)
            mapper.registerModule(simpleModule)

            try {
                val dataPlaylist = mapper.readValue(data, DataPlaylist::class.java)
                val list = dataPlaylist.data
                return list
            } catch (e: IOException) {
                Log.e(TAG, "exception occurred : ", e)
            }

            return emptyList()
        }
    }


}
