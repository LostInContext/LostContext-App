package com.lostincontext.data.playlist;

import android.text.TextUtils;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.lostincontext.commons.images.DeezerImageUrlGenerator;
import com.lostincontext.commons.images.DeezerImageUrlGenerator.DeezerImageType;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataPlaylist {

    private static final String TAG = DataPlaylist.class.getSimpleName();
    private static final int CHECKSUM_NORMAL_LENGTH = 32;

    private List<Playlist> data;

    public List<Playlist> getData() {
        return data;
    }

    public int total;

    public String next;

    public void setData(List<Playlist> data) {
        this.data = data;
    }


    public static List<Playlist> getPlaylists() {
        return deserialize(Json.PLAYLIST_RAW_JSON);
    }

    public static List<Playlist> deserialize(String data) {
        ObjectMapper mapper = new ObjectMapper();

        SimpleModule simpleModule = new SimpleModule();
        JsonDeserializer<Playlist> customDeserializer = new PlaylistDeserializer();
        simpleModule.addDeserializer(Playlist.class, customDeserializer);
        mapper.registerModule(simpleModule);

        try {
            final DataPlaylist dataPlaylist = mapper.readValue(data, DataPlaylist.class);
            return dataPlaylist.getData();
        } catch (IOException e) {
            Log.e(TAG, "exception occurred : ", e);
        }
        return Collections.emptyList();
    }

    /**
     * Extract the fields we want from the public API
     */
    private static class PlaylistDeserializer extends JsonDeserializer<Playlist> {


        private static class DataHolder {
            public String coverMd5;
            @DeezerImageType public int coverType;
        }

        DataHolder data = new DataHolder();

        @Override
        public Playlist deserialize(JsonParser p,
                                    DeserializationContext context) throws IOException {
            ObjectCodec oc = p.getCodec();
            JsonNode node = oc.readTree(p);

            extractCoverMd5(node.get("picture_small").textValue(), data);

            return new Playlist(node.get("id").asInt(),
                                node.get("title").textValue(),
                                node.get("creator").get("name").textValue(),
                                data.coverMd5,
                                data.coverType);
        }

        /**
         * dirty hack around the fact that the API can't simply return the coverMd5
         */
        private static void extractCoverMd5(String cover, DataHolder data) {
            int beginning = cover.indexOf("/cover/") + 7;
            if (beginning == 6) {
                beginning = cover.indexOf("/playlist/") + 10;
                data.coverType = DeezerImageUrlGenerator.TYPE_PLAYLIST_CUSTOM_COVER;
            } else {
                data.coverType = DeezerImageUrlGenerator.TYPE_COVER;
            }
            int end = cover.indexOf("/", beginning);
            data.coverMd5 = TextUtils.substring(cover, beginning, end);
        }


    }


}
