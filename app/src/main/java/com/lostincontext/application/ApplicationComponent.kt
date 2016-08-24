package com.lostincontext.application


import com.lostincontext.data.location.repo.LocationRepository
import com.lostincontext.data.playlist.repo.PlaylistsRepository
import com.lostincontext.data.rules.repo.RulesRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    val application: LostApplication

    val locationRepository: LocationRepository

    val rulesRepository: RulesRepository

    val playlistsRepository: PlaylistsRepository
}
