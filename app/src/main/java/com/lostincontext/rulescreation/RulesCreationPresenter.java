package com.lostincontext.rulescreation;

import android.content.Intent;
import android.graphics.Color;

import com.lostincontext.R;
import com.lostincontext.commons.list.Section;
import com.lostincontext.data.FenceCreator;
import com.lostincontext.data.location.LocationModel;
import com.lostincontext.data.location.repo.LocationRepository;
import com.lostincontext.data.playlist.Playlist;
import com.lostincontext.data.playlist.PlaylistPicker;
import com.lostincontext.data.rules.LocationFenceVM;
import com.lostincontext.rulescreation.display.FenceCreatorSection;
import com.lostincontext.rulescreation.display.PlaylistPickSection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;
import static com.lostincontext.rulescreation.RulesCreationFragment.PLACE_PICKER_REQUEST_CODE;
import static com.lostincontext.rulescreation.RulesCreationFragment.PLAYLIST_PICKER_REQUEST_CODE;

public class RulesCreationPresenter implements RulesCreationContract.Presenter {

    private RulesCreationContract.View view;
    private LocationRepository locationRepository;
    private String currentFenceName;


    @Override public void start() {

        List<FenceCreator> activities = new ArrayList<>(4);
        activities.add(new FenceCreator("Walking", R.drawable.ic_walk_24, Color.RED));
        activities.add(new FenceCreator("Running", R.drawable.ic_run_24, Color.RED));
        activities.add(new FenceCreator("On bicycle", R.drawable.ic_bike_24, Color.RED));
        activities.add(new FenceCreator("In vehicle", R.drawable.ic_car_24, Color.RED));

        FenceCreatorSection activitiesSection = new FenceCreatorSection("Activity", activities, R.id.view_type_rule_creator);


        List<FenceCreator> headphones = new ArrayList<>(2);

        headphones.add(new FenceCreator("Plugged in", R.drawable.ic_headset_24, Color.RED));
        headphones.add(new FenceCreator("Plugged out", R.drawable.ic_headset_24, Color.RED));

        FenceCreatorSection headphonesSection = new FenceCreatorSection("Headphones", headphones, R.id.view_type_rule_creator);

        List<FenceCreator> locations = new ArrayList<>(2);


        locations.add(new FenceCreator(LocationFenceVM.HOME, R.drawable.ic_home_24, Color.RED));
        locations.add(new FenceCreator(LocationFenceVM.WORK, R.drawable.ic_work_24, Color.RED));

        FenceCreatorSection locationSection = new FenceCreatorSection("Location", locations, R.id.view_type_rule_creator);


        List<PlaylistPicker> playlistPickers = new ArrayList<>(1);

        playlistPickers.add(new PlaylistPicker(null));

        PlaylistPickSection playlistPickerSection = new PlaylistPickSection("pick a playlist", playlistPickers);

        List<Section> sections = Arrays.<Section>asList(activitiesSection,
                                                        headphonesSection,
                                                        locationSection,
                                                        playlistPickerSection);
        view.setSections(sections);
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                view.setPlace(data);
            }
        }
        if (requestCode == PLAYLIST_PICKER_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                view.setPlayList(data);
            }
        }
    }


    @Override public void onRuleCreationItemClick(FenceCreator fence) {
        currentFenceName = fence.name;
        switch (fence.name) {
            case LocationFenceVM.HOME:
            case LocationFenceVM.WORK:
                LocationModel locationModel = getLocation(fence.name);
                if (locationModel == null) {
                    view.showLocationPicker(fence.name);
//                    LocationFenceVM locationFenceVM = new LocationFenceVM(LocationFenceVM.HOME, locationModel.getLatLng());
                } else {
                    view.showToast(locationModel.getPlaceName());
//                    LocationFenceVM locationFenceVM = new LocationFenceVM(fence.name, locationModel.getLatLng());
                }
                break;

        }
    }

    @Override public LocationRepository getLocationRepository() {
        return locationRepository;
    }

    @Override public void getPlayList(Intent intent) {
        if(intent.getExtras()!=null) {
            String playlist1 = intent.getExtras().getString("playlist");
            try {
                PlaylistPicker playlistPicker = PlaylistPicker.deserialize(playlist1);
                Playlist playlist = playlistPicker.getPlaylist();//TODO there it is
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private LocationModel getLocation(String locationName) {
        LocationModel location = null;
        try {
            location = locationRepository.getLocation(locationName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return location;
    }

    @Override public void onPlaylistPickerClick() {
        view.onPlaylistPickerClick();
    }

    @Inject
    RulesCreationPresenter(RulesCreationContract.View view, LocationRepository locationRepository) {
        this.view = view;
        this.locationRepository = locationRepository;
    }

    @Inject void setup() {
        view.setPresenter(this);
    }

}
