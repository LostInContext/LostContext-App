package com.lostincontext.data.playlist.repo;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

import dagger.Module;
import dagger.Provides;

@Module
public class PlaylistRepositoryModule {

    private Context context;

    public PlaylistRepositoryModule(Context context) {
        this.context = context;
    }

    @Provides ObjectMapper provideObjectMapper() {
        return new ObjectMapper();
    }


}
