package com.lostincontext.data.rules.repo;


import android.content.Context;
import android.content.SharedPreferences;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static android.content.Context.MODE_PRIVATE;

@Module
public class RulesRepositoryModule {


    public RulesRepositoryModule() { }

    @Singleton
    @Named("rule")
    @Provides
    ObjectMapper provideObjectMapper() {
        return new ObjectMapper();
    }

    @Singleton
    @Named("rule")
    @Provides
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences("rules", MODE_PRIVATE);
    }

}