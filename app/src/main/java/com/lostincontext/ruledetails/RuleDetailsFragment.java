package com.lostincontext.ruledetails;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lostincontext.R;
import com.lostincontext.data.playlist.Playlist;
import com.lostincontext.databinding.RuleDetailsScreenFragmentBinding;
import com.lostincontext.playlists.PlaylistsActivity;
import com.lostincontext.ruledetails.items.FenceItem;

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.lostincontext.playlists.PlaylistsContract.EXTRA_PLAYLIST;


public class RuleDetailsFragment extends Fragment implements RuleDetailsContract.View {

    private static final int PLAYLIST_PICKER_REQUEST_CODE = 9001;
    private RuleDetailsContract.Presenter presenter;

    private RuleDetailsAdapter adapter;


    public static RuleDetailsFragment newInstance() {
        return new RuleDetailsFragment();
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        RuleDetailsScreenFragmentBinding binding = DataBindingUtil.inflate(inflater,
                                                                           R.layout.rule_details_screen_fragment,
                                                                           container,
                                                                           false);

        RecyclerView recyclerView = binding.recyclerView;

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RuleDetailsAdapter(presenter);


        binding.plusButton.setCallback(presenter);
        recyclerView.setAdapter(adapter);


        return binding.getRoot();
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void setPresenter(RuleDetailsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override public void setItems(List<FenceItem> items) {
        adapter.setItems(items);
    }

    @Override public void notifyItemInserted(int position) {
        adapter.notifyItemInserted(position);
    }

    @Override public void notifyItemChanged(int position) {
        adapter.notifyItemChanged(position);
    }

    @Override public void showPlaylist(Playlist playlist) {
        adapter.setPlaylist(playlist);
    }

    @Override public void notifyItemRangeInserted(int positionStart, int itemCount) {
        adapter.notifyItemRangeInserted(positionStart, itemCount);
    }

    @Override public void displayFenceChoice() {
        PickerDialogFragment picker = PickerDialogFragment.newInstance();
        picker.registerCallback(presenter);
        picker.show(getFragmentManager(), "PickerDialogFragment");
    }

    @Override public void pickAPlaylist() {
        Intent intent = new Intent(this.getContext(), PlaylistsActivity.class);
        startActivityForResult(intent, PLAYLIST_PICKER_REQUEST_CODE);
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLAYLIST_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            Playlist playlist = data.getParcelableExtra(EXTRA_PLAYLIST);
            presenter.onPlaylistPicked(playlist);
        }

    }
}
