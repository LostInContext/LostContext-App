package com.lostincontext.data;

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
        return deserialize(Playlist.JSON);
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

        @Override
        public Playlist deserialize(JsonParser p,
                                    DeserializationContext ctxt) throws IOException {
            ObjectCodec oc = p.getCodec();
            JsonNode node = oc.readTree(p);


            Playlist playlist = new Playlist();
            String coverMd5 = extractCoverMd5(node.get("picture_small").textValue());
            playlist.setCoverMd5(coverMd5);
            playlist.setTitle(node.get("title").textValue());
            playlist.setImageType(extractCoverType(coverMd5));
            playlist.setId(node.get("id").asInt());

            return playlist;
        }

        private static String extractCoverMd5(String cover) {
            int beginning = cover.indexOf("/cover/") + 7;
            if (beginning == 6) beginning = cover.indexOf("/playlist/") + 10;
            int end = cover.indexOf("/", beginning);
            return TextUtils.substring(cover, beginning, end);
        }

        private static int extractCoverType(String coverMd5) {

            if (coverMd5.length() > (CHECKSUM_NORMAL_LENGTH * 2)) {
                return (DeezerImageUrlGenerator.TYPE_COVER);
            } else {
                return (DeezerImageUrlGenerator.TYPE_PLAYLIST_CUSTOM_COVER);
            }
        }
    }


}
