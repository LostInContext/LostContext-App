package com.lostincontext.application


import com.lostincontext.data.location.repo.LocationRepository
import com.lostincontext.data.rules.repo.RulesRepository

import javax.inject.Singleton

import dagger.Component

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    val application: LostApplication

    val locationRepository: LocationRepository

    val rulesRepository: RulesRepository
}
