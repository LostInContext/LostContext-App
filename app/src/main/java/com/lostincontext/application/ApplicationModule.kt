package com.lostincontext.application

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.res.Resources
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.lostincontext.data.location.repo.LocationRepository
import com.lostincontext.data.playlist.PlaylistJsonAdapter
import com.lostincontext.data.playlist.repo.PlaylistsRepository
import com.lostincontext.data.rules.repo.RulesRepository
import com.lostincontext.data.user.UserImageAdapter
import com.lostincontext.data.user.repo.DeezerPlaylistsEndPoint
import com.lostincontext.data.user.repo.DeezerUserSearchEndPoint
import com.lostincontext.data.user.repo.UserRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApplicationModule(private val lostApplication: LostApplication) {


    @Provides
    @Singleton
    fun provideApplication(): LostApplication = lostApplication


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

    @Singleton
    @Provides
    fun providePlaylistsRepository(resources: Resources,
                                   playlistsEndPoint: DeezerPlaylistsEndPoint): PlaylistsRepository {
        return PlaylistsRepository(resources, playlistsEndPoint)
    }

    //region userRepo
    @Singleton
    @Provides
    fun provideUserRepository(searchEndPoint: DeezerUserSearchEndPoint): UserRepository {
        return UserRepository(searchEndPoint)
    }


    @Provides
    internal fun provideRetrofit(): Retrofit {

        val moshi = Moshi.Builder()
                .add(UserImageAdapter())
                .add(PlaylistJsonAdapter())
                .build()

        return Retrofit.Builder()
                .baseUrl("https://api.deezer.com/")
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
    }

    @Provides fun provideUserSearch(retrofit: Retrofit): DeezerUserSearchEndPoint {
        return retrofit.create(DeezerUserSearchEndPoint::class.java)
    }

    @Provides fun providePlaylistsEndPoint(retrofit: Retrofit): DeezerPlaylistsEndPoint {
        return retrofit.create(DeezerPlaylistsEndPoint::class.java)
    }

    //endregion

    @Provides internal fun provideObjectMapper(): ObjectMapper = jacksonObjectMapper()


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

    @Provides
    internal fun provideResourcesForPlaylists(): Resources = lostApplication.resources


}
