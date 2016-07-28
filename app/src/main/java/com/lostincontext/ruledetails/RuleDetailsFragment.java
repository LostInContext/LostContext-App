package com.lostincontext.ruledetails;

import android.app.PendingIntent;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.lostincontext.R;
import com.lostincontext.data.RuleDetails;
import com.lostincontext.data.playlist.Playlist;
import com.lostincontext.databinding.RuleDetailsScreenFragmentBinding;
import com.lostincontext.playlists.PlaylistsActivity;
import com.lostincontext.ruledetails.items.FenceItem;
import com.lostincontext.that.ThatService;

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.text.TextUtils.isEmpty;
import static com.lostincontext.playlists.PlaylistsContract.EXTRA_PLAYLIST;


public class RuleDetailsFragment extends Fragment implements RuleDetailsContract.View {

    private static final int PLAYLIST_PICKER_REQUEST_CODE = 9001;
    private static final int LOCATION_PICKER_REQUEST_CODE = 9002;


    private RuleDetailsContract.Presenter presenter;
    private RuleDetailsScreenFragmentBinding binding;
    private RuleDetailsAdapter adapter;

    private String savedPlaceName;


    public static RuleDetailsFragment newInstance() {
        return new RuleDetailsFragment();
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
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

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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

    @Override public void showLocationPicker(String name) {
        savedPlaceName = name;
        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            Intent intent = builder.build(getActivity());
            startActivityForResult(intent, LOCATION_PICKER_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    @Override public void setRuleDetails(RuleDetails ruleDetails) {
        binding.setRule(ruleDetails);
    }

        public PendingIntent getPendingIntent(Playlist playlist) {
            Intent intent = new Intent(this.getContext(), ThatService.class);
            return PendingIntent.getService(this.getContext().getApplicationContext(),
                                            0,
                                            intent,
                                            0);
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLAYLIST_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            Playlist playlist = data.getParcelableExtra(EXTRA_PLAYLIST);
            presenter.onPlaylistPicked(playlist);
        } else if (requestCode == LOCATION_PICKER_REQUEST_CODE
                && resultCode == RESULT_OK
                && !isEmpty(savedPlaceName)) {
            Place place = PlacePicker.getPlace(getContext(), data);
            LatLng latLng = place.getLatLng();
            presenter.onPlacePicked(savedPlaceName, latLng);
        }

    }

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.edit_rule_menu, menu);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        return presenter.onMenuItemClick(item.getItemId());
    }
}
