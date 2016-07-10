package com.lostincontext.playlists;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.lostincontext.R;
import com.lostincontext.commons.BaseActivity;

import javax.inject.Inject;

import static com.lostincontext.utils.Activities.addFragmentToActivity;


public class PlaylistsActivity extends BaseActivity {


    @Inject PlaylistsPresenter presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlists_screen);

        FragmentManager fm = getSupportFragmentManager();
        PlaylistsFragment fragment = (PlaylistsFragment) fm.findFragmentById(R.id.contentFrame);

        if (fragment == null) {
            fragment = PlaylistsFragment.newInstance();
            addFragmentToActivity(fm, fragment, R.id.contentFrame);
        }

        DaggerPlaylistsComponent.builder()
                .playlistsPresenterModule(new PlaylistsPresenterModule(fragment))
                .build()
                .inject(this);
    }
}
