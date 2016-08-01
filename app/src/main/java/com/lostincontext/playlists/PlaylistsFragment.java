package com.lostincontext.playlists;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lostincontext.PlaylistLauncher;
import com.lostincontext.R;
import com.lostincontext.commons.list.SpacesItemDecoration;
import com.lostincontext.data.playlist.Playlist;
import com.lostincontext.databinding.PlaylistsScreenFragmentBinding;

import java.util.List;


public class PlaylistsFragment extends Fragment implements PlaylistsContract.View {

    private PlaylistsContract.Presenter presenter;

    private PlaylistsAdapter adapter;


    public static PlaylistsFragment newInstance() {
        return new PlaylistsFragment();
    }


    public PlaylistsFragment() { }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        PlaylistsScreenFragmentBinding binding = DataBindingUtil.inflate(inflater,
                                                                         R.layout.playlists_screen_fragment,
                                                                         container,
                                                                         false);
        RecyclerView recyclerView = binding.recyclerView;
        Resources resources = getResources();
        final int span = resources.getInteger(R.integer.grid_span);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), span);
        recyclerView.setLayoutManager(layoutManager);
        int space = resources.getDimensionPixelSize(R.dimen.grid_spacing);
        recyclerView.addItemDecoration(new SpacesItemDecoration(space, span));

        adapter = new PlaylistsAdapter(presenter, presenter);

        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override public int getSpanSize(int position) {
                switch (adapter.getCurrentState()) {
                    case LOADING:
                    case ERROR:
                    case EMPTY:
                        return span;

                    case CONTENT:
                        return 1;

                    default:
                        throw new RuntimeException("invalid state");
                }
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        return binding.getRoot();
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override public void setPresenter(PlaylistsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override public void setPlaylists(List<Playlist> playlists) {
        adapter.setPlaylists(playlists);
    }

    @Override public void openDeezerFor(Playlist playlist) {
        PlaylistLauncher launcher = new PlaylistLauncher();
        launcher.launchPlaylist(getContext(), playlist, false);
    }

    @Override public void returnResult(Playlist playlist) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("extra", "coin coin");
        returnIntent.putExtra(PlaylistsContract.EXTRA_PLAYLIST, playlist);
        FragmentActivity activity = getActivity();
        activity.setResult(Activity.RESULT_OK, returnIntent);
        activity.finish();
    }
}
