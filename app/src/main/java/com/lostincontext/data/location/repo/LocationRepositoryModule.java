package com.lostincontext.data.location.repo;


import android.content.Context;
import android.content.SharedPreferences;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

import static android.content.Context.MODE_PRIVATE;

@Module
public class LocationRepositoryModule {
    private Context context;

    public LocationRepositoryModule(Context context) {
        this.context = context;
    }

    @Named("location")
    @Provides ObjectMapper provideObjectMapper() {
        return new ObjectMapper();
    }


    @Named("location")
    @Provides SharedPreferences provideSharedPreferences() {
        return context.getSharedPreferences("location",
                                            MODE_PRIVATE);
    }
}
