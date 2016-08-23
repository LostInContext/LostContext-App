package com.lostincontext.application

import android.content.SharedPreferences

import com.fasterxml.jackson.databind.ObjectMapper
import com.lostincontext.data.location.repo.LocationRepository
import com.lostincontext.data.rules.repo.RulesRepository

import javax.inject.Named
import javax.inject.Singleton

import dagger.Module
import dagger.Provides

import android.content.Context.MODE_PRIVATE
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

@Module
class ApplicationModule(private val lostApplication: LostApplication) {


    @Provides
    @Singleton
    fun provideApplication(): LostApplication {
        return lostApplication
    }


    @Singleton
    @Provides
    fun provideLocationRepository(@Named("location") preferences: SharedPreferences,
                                  mapper: ObjectMapper): LocationRepository {
        return LocationRepository(preferences, mapper)
    }

    @Singleton
    @Provides
    fun provideRulesRepository(@Named("rules") preferences: SharedPreferences,
                               mapper: ObjectMapper): RulesRepository {
        return RulesRepository(preferences, mapper)
    }

    @Provides internal fun provideObjectMapper(): ObjectMapper {
        return jacksonObjectMapper()
    }


    @Named("location")
    @Provides
    internal fun provideSharedPreferencesForLocation(): SharedPreferences {
        return lostApplication.getSharedPreferences("location",
                                                    MODE_PRIVATE)
    }


    @Named("rules")
    @Provides
    internal fun provideSharedPreferencesForRules(): SharedPreferences {
        return lostApplication.getSharedPreferences("rules",
                                                    MODE_PRIVATE)
    }

}
