package com.lostincontext.ruledetails;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.lostincontext.R;
import com.lostincontext.data.GridBottomSheetItem;
import com.lostincontext.data.playlist.Playlist;
import com.lostincontext.data.rules.Rule;
import com.lostincontext.databinding.RuleDetailsScreenFragmentBinding;
import com.lostincontext.playlists.PlaylistsActivity;
import com.lostincontext.ruledetails.RuleDetailsContract.RuleErrors;
import com.lostincontext.ruledetails.items.FenceItem;
import com.lostincontext.that.ThatService;

import java.util.EnumSet;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.app.Activity.RESULT_OK;
import static android.support.v4.content.ContextCompat.checkSelfPermission;
import static android.text.TextUtils.isEmpty;
import static com.lostincontext.playlists.PlaylistsContract.EXTRA_PLAYLIST;


public class RuleDetailsFragment extends Fragment implements RuleDetailsContract.View {

    private static final String TAG = RuleDetailsFragment.class.getSimpleName();

    private static final int PLAYLIST_PICKER_REQUEST_CODE = 9001;
    private static final int LOCATION_PICKER_REQUEST_CODE = 9002;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 9003;
    private static final int PLAY_SERVICES_REQUEST_CODE = 9004;

    private static final String[] FINE_LOCATION_ARRAY = new String[]{ACCESS_FINE_LOCATION};


    private RuleDetailsContract.Presenter presenter;
    private RuleDetailsScreenFragmentBinding binding;
    private RuleDetailsAdapter adapter;

    private String savedPlaceName;
    private GridBottomSheetItem savedGridBottomSheetItem;


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

    @Override public void notifyItemChanged(int position, Object payload) {
        adapter.notifyItemChanged(position, payload);
    }

    @Override public void showPlaylist(Playlist playlist) {
        adapter.setPlaylist(playlist);
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

    @Override
    public void checkPermissionsAndShowLocationPicker(String name, GridBottomSheetItem item) {

        savedPlaceName = name;
        savedGridBottomSheetItem = item;
        checkLocationPermissionToShowPicker();
    }

    @Override public void setRule(Rule rule) {
        binding.setRule(rule);
    }

    @Override public PendingIntent getPendingIntent(Playlist playlist) {
        Intent intent = new Intent(this.getContext(), ThatService.class);
        return PendingIntent.getService(this.getContext().getApplicationContext(),
                                        0,
                                        intent,
                                        0);
    }

    @Override public void finishActivity() {
        getActivity().finish();
    }

    @Override public void showSnack(EnumSet<RuleErrors> errors) {

        String message = "";
        if (errors.contains(RuleErrors.NO_TITLE)) {
            message = getString(R.string.missing_title);
        } else if (errors.contains(RuleErrors.NO_CONDITION)) {
            message = getString(R.string.missing_condition);
        } else if (errors.contains(RuleErrors.NO_PLAYLIST)) {
            message = getString(R.string.missing_playlist);
        } else if (errors.contains(RuleErrors.SAVE_ERROR)) {
            message = getString(R.string.rule_save_error);
        }

        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
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
            presenter.onPlacePicked(savedPlaceName, savedGridBottomSheetItem, latLng);
        } else if (requestCode == PLAY_SERVICES_REQUEST_CODE
                && resultCode == RESULT_OK
                && !isEmpty(savedPlaceName)) {
            showLocationPicker();
        }
    }

    @Override public void onRequestPermissionsResult(int requestCode,
                                                     @NonNull String[] permissions,
                                                     @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE
                && grantResults.length == 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            showLocationPicker();
        }
    }

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.edit_rule_menu, menu);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        return presenter.onMenuItemClick(item.getItemId());
    }


    private void checkLocationPermissionToShowPicker() {
        int permissionResult = checkSelfPermission(getContext(),
                                                   ACCESS_FINE_LOCATION);
        boolean permissionGranted = permissionResult == PackageManager.PERMISSION_GRANTED;
        if (permissionGranted) showLocationPicker();
        else {
            Log.d(TAG, "getLocationPermission: permissionNeeded");
            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                Snackbar.make(binding.getRoot(),
                              R.string.permission_rationale,
                              Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.ok,
                                   new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           requestPermissions(FINE_LOCATION_ARRAY,
                                                              LOCATION_PICKER_REQUEST_CODE);
                                       }
                                   })
                        .show();

            } else {
                requestPermissions(FINE_LOCATION_ARRAY,
                                   LOCATION_PERMISSION_REQUEST_CODE);
            }
        }

    }

    private void showLocationPicker() {

        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            Intent intent = builder.build(getActivity());
            startActivityForResult(intent, LOCATION_PICKER_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(getActivity(),
                                                                               e.getConnectionStatusCode(),
                                                                               PLAY_SERVICES_REQUEST_CODE);
            dialog.show();
        } catch (GooglePlayServicesNotAvailableException e) {
            Snackbar.make(binding.getRoot(), R.string.play_services_not_available,
                          Snackbar.LENGTH_LONG)
                    .show();
        }
    }


}
