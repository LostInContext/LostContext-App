package com.lostincontext.data.location.repo;


import android.content.Context;
import android.content.SharedPreferences;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static android.content.Context.MODE_PRIVATE;

@Module
public class LocationRepositoryModule {


    public LocationRepositoryModule() { }

    @Singleton
    @Named("location")
    @Provides
    ObjectMapper provideObjectMapper() {
        return new ObjectMapper();
    }

    @Singleton
    @Named("location")
    @Provides
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences("location", MODE_PRIVATE);
    }
}
