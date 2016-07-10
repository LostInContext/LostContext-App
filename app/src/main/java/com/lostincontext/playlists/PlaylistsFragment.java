package com.lostincontext.playlists;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lostincontext.R;
import com.lostincontext.databinding.PlaylistsScreenFragmentBinding;


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
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new PlaylistsAdapter(presenter);

        recyclerView.setAdapter(adapter);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override public int getSpanSize(int position) {
                switch (adapter.getCurrentState()) {
                    case LOADING:
                    case ERROR:
                    case EMPTY:
                        return 2;

                    case CONTENT:
                        return 1;

                    default:
                        throw new RuntimeException("invalid state");
                }
            }
        });
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
}
