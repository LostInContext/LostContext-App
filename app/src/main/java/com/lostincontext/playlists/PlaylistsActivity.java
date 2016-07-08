package com.lostincontext.playlists;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;

import com.lostincontext.R;
import com.lostincontext.commons.BaseActivity;

import javax.inject.Inject;

import static com.lostincontext.utils.Activities.addFragmentToActivity;


public class PlaylistsActivity extends BaseActivity {


    @Inject PlaylistsPresenter presenter;

    @Override public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
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
