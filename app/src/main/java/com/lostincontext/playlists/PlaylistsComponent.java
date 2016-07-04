package com.lostincontext.playlists;


import com.lostincontext.commons.FragmentScope;

import dagger.Component;

@FragmentScope
@Component(modules = PlaylistsPresenterModule.class)
public interface PlaylistsComponent {

    void inject(PlaylistsActivity activity);
}