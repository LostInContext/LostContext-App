package com.lostincontext.playlists


import com.lostincontext.application.ApplicationComponent
import com.lostincontext.commons.FragmentScope

import dagger.Component

@FragmentScope
@Component(modules = arrayOf(PlaylistsPresenterModule::class),
           dependencies = arrayOf(ApplicationComponent::class))
interface PlaylistsComponent {

    fun inject(activity: PlaylistsActivity)
}