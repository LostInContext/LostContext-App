package com.lostincontext.application;

import android.content.SharedPreferences;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lostincontext.data.location.repo.LocationRepository;
import com.lostincontext.data.rules.repo.RulesRepository;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static android.content.Context.MODE_PRIVATE;

@Module
public final class ApplicationModule {

    private final LostApplication lostApplication;

    public ApplicationModule(LostApplication lostApplication) {
        this.lostApplication = lostApplication;
    }


    @Provides
    @Singleton
    public LostApplication provideApplication() {
        return lostApplication;
    }


    @Singleton
    @Provides
    public LocationRepository provideLocationRepository(@Named("location") SharedPreferences preferences,
                                                        ObjectMapper mapper) {
        return new LocationRepository(preferences, mapper);
    }

    @Singleton
    @Provides
    public RulesRepository provideRulesRepository(@Named("rules") SharedPreferences preferences,
                                                  ObjectMapper mapper) {
        return new RulesRepository(preferences, mapper);
    }

    @Provides ObjectMapper provideObjectMapper() {
        return new ObjectMapper();
    }


    @Named("location")
    @Provides
    SharedPreferences provideSharedPreferencesForLocation() {
        return lostApplication.getSharedPreferences("location",
                                                    MODE_PRIVATE);
    }


    @Named("rules")
    @Provides SharedPreferences provideSharedPreferencesForRules() {
        return lostApplication.getSharedPreferences("rules",
                                                    MODE_PRIVATE);
    }

}
