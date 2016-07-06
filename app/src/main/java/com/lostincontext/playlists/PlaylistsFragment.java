package com.lostincontext.playlists;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lostincontext.R;


public class PlaylistsFragment extends Fragment implements PlaylistsContract.View {


    private PlaylistsContract.Presenter presenter;


    public static PlaylistsFragment newInstance() {
        return new PlaylistsFragment();
    }


    public PlaylistsFragment() { }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_screen, container, false); //TODO

        return root;
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override public void setPresenter(PlaylistsContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
