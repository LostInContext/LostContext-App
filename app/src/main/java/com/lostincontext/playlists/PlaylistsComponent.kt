package com.lostincontext.playlists


import com.lostincontext.commons.FragmentScope

import dagger.Component

@FragmentScope
@Component(modules = arrayOf(PlaylistsPresenterModule::class))
interface PlaylistsComponent {

    fun inject(activity: PlaylistsActivity)
}