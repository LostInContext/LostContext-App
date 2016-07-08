package com.lostincontext.data.rules.repo;


import android.content.Context;
import android.content.SharedPreferences;

import com.fasterxml.jackson.databind.ObjectMapper;

import dagger.Module;
import dagger.Provides;

import static android.content.Context.MODE_PRIVATE;

@Module
public class RulesRepositoryModule {

    private Context context;

    public RulesRepositoryModule(Context context) {
        this.context = context;
    }


    @Provides ObjectMapper provideObjectMapper() {
        return new ObjectMapper();
    }


    @Provides SharedPreferences provideSharedPreferences() {
        return context.getSharedPreferences(context.getPackageName(),
                                            MODE_PRIVATE);
    }

}