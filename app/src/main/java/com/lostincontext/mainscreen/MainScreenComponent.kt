package com.lostincontext.mainscreen

import com.lostincontext.application.ApplicationComponent
import com.lostincontext.commons.FragmentScope

import dagger.Component

@FragmentScope
@Component(modules = arrayOf(MainScreenPresenterModule::class),
           dependencies = arrayOf(ApplicationComponent::class))
interface MainScreenComponent {

    fun inject(fragment : MainScreenFragment)
}