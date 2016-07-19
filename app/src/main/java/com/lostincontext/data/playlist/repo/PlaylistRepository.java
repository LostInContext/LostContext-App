package com.lostincontext.data.playlist.repo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lostincontext.data.playlist.PlaylistPicker;

import java.io.IOException;

import javax.inject.Inject;

public class PlaylistRepository {


    private final ObjectMapper objectMapper;

    @Inject public PlaylistRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    //region Serializer / deserializer

    private String serialize(PlaylistPicker playlistPicker) throws JsonProcessingException {
        return objectMapper.writeValueAsString(playlistPicker);

    }

    private PlaylistPicker deserialize(String json) throws IOException {
        return objectMapper.readerFor(PlaylistPicker.class).readValue(json);
    }
    //endregion

}
