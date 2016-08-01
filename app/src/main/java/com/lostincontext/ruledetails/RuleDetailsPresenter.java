package com.lostincontext.ruledetails;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.awareness.fence.FenceUpdateRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.lostincontext.R;
import com.lostincontext.awareness.Awareness;
import com.lostincontext.commons.list.Section;
import com.lostincontext.data.GridBottomSheetItem;
import com.lostincontext.data.location.LocationModel;
import com.lostincontext.data.location.repo.LocationRepository;
import com.lostincontext.data.location.repo.LocationRepository.LocationCallback;
import com.lostincontext.data.playlist.Playlist;
import com.lostincontext.data.rules.CompositeFenceVM;
import com.lostincontext.data.rules.CompositeFenceVM.Operator;
import com.lostincontext.data.rules.DetectedActivityFenceVM;
import com.lostincontext.data.rules.FenceBuilder;
import com.lostincontext.data.rules.FenceVM;
import com.lostincontext.data.rules.HeadphoneFenceVM;
import com.lostincontext.data.rules.LocationFenceVM;
import com.lostincontext.data.rules.Rule;
import com.lostincontext.data.rules.repo.RulesRepository;
import com.lostincontext.ruledetails.items.FenceItem;
import com.lostincontext.ruledetails.items.FenceItem.Link;
import com.lostincontext.ruledetails.pick.BottomSheetItemSection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import javax.inject.Inject;

import static com.google.common.collect.Lists.newArrayList;
import static com.lostincontext.ruledetails.RuleDetailsContract.LINK_CHANGED;
import static com.lostincontext.ruledetails.RuleDetailsContract.Presenter;
import static com.lostincontext.ruledetails.RuleDetailsContract.RuleErrors;
import static com.lostincontext.ruledetails.RuleDetailsContract.View;
import static com.lostincontext.ruledetails.items.FenceItem.Link.AND;
import static com.lostincontext.ruledetails.items.FenceItem.Link.AND_NOT;
import static com.lostincontext.ruledetails.items.FenceItem.Link.OR_NOT;
import static com.lostincontext.ruledetails.items.FenceItem.Link.WHEN;
import static java.util.EnumSet.of;

public class RuleDetailsPresenter implements Presenter, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    private static final String TAG = RuleDetailsPresenter.class.getSimpleName();

    private final View view;

    private final Rule rule = new Rule();

    private final LocationRepository locationRepository;
    private final RulesRepository rulesRepository;
    private final Awareness awareness;

    private List<FenceItem> items = new ArrayList<>();
    private Playlist playlist;


    @Inject RuleDetailsPresenter(View view,
                                 LocationRepository locationRepository,
                                 RulesRepository rulesRepository,
                                 Awareness awareness) {
        this.view = view;
        this.locationRepository = locationRepository;
        this.rulesRepository = rulesRepository;
        this.awareness = awareness;
    }

    @Inject void setup() {
        view.setPresenter(this);
        awareness.init(this,
                       this);
    }


    @Override public void start() {
        view.setItems(items);
        view.setRule(rule);
    }

    @Override public void onLinkClick(FenceItem item) {
        toggleLink(item);
    }


    public void toggleLink(FenceItem item) {
        switch (item.link) {
            case AND:
                item.link = Link.OR;
                break;
            case OR:
                item.link = AND_NOT;
                break;
            case AND_NOT:
                item.link = OR_NOT;
                break;
            case OR_NOT:
                item.link = AND;
                break;
            case WHEN:
                break;
        }

        view.notifyItemChanged(items.indexOf(item), LINK_CHANGED);


    }

    @Override public void onPlusButtonClick() {
        view.displayFenceChoice();
    }

    @Override public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "onConnected");
    }

    @Override public void onConnectionSuspended(int i) {
        Log.d(TAG, "onConnectionSuspended " + i);
    }

    @Override public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionSuspended " + connectionResult.getErrorMessage());
    }

    public enum Picker {
        WALK,
        RUN,
        BIKE,
        CAR,
        PLUG_IN,
        PLUG_OUT,
        HOME,
        WORK,
        PLAYLIST
    }

    @Override public List<Section> provideFenceChoices() {

        List<GridBottomSheetItem> choices = newArrayList(new GridBottomSheetItem("Walking",
                                                                                 R.drawable.ic_walk_24,
                                                                                 Picker.WALK),
                                                         new GridBottomSheetItem("Running",
                                                                                 R.drawable.ic_run_24,
                                                                                 Picker.RUN),
                                                         new GridBottomSheetItem("On bicycle",
                                                                                 R.drawable.ic_bike_24,
                                                                                 Picker.BIKE),
                                                         new GridBottomSheetItem("In vehicle",
                                                                                 R.drawable.ic_car_24,
                                                                                 Picker.CAR),
                                                         new GridBottomSheetItem("Plugged in",
                                                                                 R.drawable.ic_headset_24,
                                                                                 Picker.PLUG_IN),
                                                         new GridBottomSheetItem("Plugged out",
                                                                                 R.drawable.ic_headset_24,
                                                                                 Picker.PLUG_OUT),
                                                         new GridBottomSheetItem("At home",
                                                                                 R.drawable.ic_home_24,
                                                                                 Picker.HOME),
                                                         new GridBottomSheetItem("At work",
                                                                                 R.drawable.ic_work_24,
                                                                                 Picker.WORK));

        BottomSheetItemSection fencesSection = new BottomSheetItemSection("Pick a condition", choices);


        List<GridBottomSheetItem> playlistPickers = newArrayList(new GridBottomSheetItem("Playlist",
                                                                                         R.drawable.ic_music_note_24,
                                                                                         Picker.PLAYLIST));

        BottomSheetItemSection mediaPickSection = new BottomSheetItemSection("Pick a playlist", playlistPickers);

        return Arrays.<Section>asList(fencesSection,
                                      mediaPickSection);
    }


    @Override public void onGridBottomSheetItemClick(GridBottomSheetItem item) {
        switch (item.picker) {

            case WALK:
            case RUN:
            case BIKE:
            case CAR:
            case PLUG_IN:
            case PLUG_OUT:
                FenceItem fenceItem = FenceItem.createFromPick(item, getFenceVMForPick(item), items.isEmpty());
                items.add(fenceItem);
                view.notifyItemInserted(items.indexOf(fenceItem));
                break;

            case HOME:
            case WORK:
                handleLocationItemClick(item);
                break;

            case PLAYLIST:
                view.pickAPlaylist();
                break;
        }
    }


    private void handleLocationItemClick(final GridBottomSheetItem item) {
        String name = item.picker.name();
        locationRepository.getLocation(name, new LocationCallback() {
            @Override public void onLocationFetched(LocationModel locationModel) {
                addLocationFence(item, locationModel);
            }

            @Override public void onLocationLoadFailed(String name) {
                view.checkPermissionsAndShowLocationPicker(name, item);
            }
        });
    }

    private void addLocationFence(GridBottomSheetItem item, LocationModel locationModel) {
        FenceVM fenceVM = new LocationFenceVM(locationModel.placeName, locationModel.getLatLng());
        FenceItem fenceItem = FenceItem.createFromPick(item, fenceVM, items.isEmpty());
        items.add(fenceItem);
        view.notifyItemInserted(items.indexOf(fenceItem));

    }


    private FenceVM getFenceVMForPick(GridBottomSheetItem pick) {
        switch (pick.picker) {

            case WALK:
                return new DetectedActivityFenceVM(DetectedActivityFenceVM.Type.WALKING,
                                                   DetectedActivityFenceVM.State.DURING);

            case RUN:
                return new DetectedActivityFenceVM(DetectedActivityFenceVM.Type.RUNNING,
                                                   DetectedActivityFenceVM.State.DURING);
            case BIKE:
                return new DetectedActivityFenceVM(DetectedActivityFenceVM.Type.ON_BICYCLE,
                                                   DetectedActivityFenceVM.State.DURING);
            case CAR:
                return new DetectedActivityFenceVM(DetectedActivityFenceVM.Type.IN_VEHICLE,
                                                   DetectedActivityFenceVM.State.DURING);

            case PLUG_IN:
                return new HeadphoneFenceVM(HeadphoneFenceVM.State.PLUGGED_IN);

            case PLUG_OUT:
                return new HeadphoneFenceVM(HeadphoneFenceVM.State.PLUGGED_OUT);

        }
        throw new RuntimeException("surprise !");
    }

    @Override public void onPlaylistPicked(Playlist playlist) {
        this.playlist = playlist;
        view.showPlaylist(playlist);
    }

    @Override public void onPlacePicked(String placeName, GridBottomSheetItem item, LatLng latLng) {
        locationRepository.saveLocation(placeName, latLng);
        addLocationFence(item, new LocationModel(placeName, latLng.latitude, latLng.longitude));
    }


    @Override public boolean onMenuItemClick(int itemId) {
        switch (itemId) {
            case R.id.action_save:
                saveRuleAndQuit();
                return true;
        }
        return false;
    }


    private void deleteRule() { }

    // todo validate input and diplay snackbar when there is an issue
    private void saveRuleAndQuit() {
        EnumSet<RuleErrors> errors = EnumSet.noneOf(RuleErrors.class);
        if (items.isEmpty()) errors.add(RuleErrors.NO_CONDITION);
        if (playlist == null) errors.add(RuleErrors.NO_PLAYLIST);
        if (TextUtils.isEmpty(rule.getName())) errors.add(RuleErrors.NO_TITLE);

        if (!errors.isEmpty()) {
            view.showSnack(errors);
            return;
        }
        rule.setPlaylist(playlist);

        FenceVM fenceVM = extractFenceForRule();
        rule.setFenceVM(fenceVM);

        FenceUpdateRequest.Builder builder = new FenceUpdateRequest.Builder();
        builder.addFence(rule.getName(), rule.getFenceVM().build(new FenceBuilder()), view.getPendingIntent(playlist));
        awareness.updateFences(builder.build()).setResultCallback(new ResultCallbacks<Status>() {
            @Override public void onSuccess(@NonNull Status status) {
                Log.d(TAG, "updateFences.onSuccess: " + status.getStatusMessage());
                rulesRepository.saveRule(rule);
                view.finishActivity();
            }

            @Override public void onFailure(@NonNull Status status) {
                view.showSnack(of(RuleErrors.SAVE_ERROR));
            }
        });

    }


    private FenceVM extractFenceForRule() {
        FenceVM completedFence = null;
        FenceVM fenceToAccumulate;
        List<FenceItem> list = getCleanedList();
        for (int i = 0, count = list.size(); i < count; i++) {
            FenceItem item = list.get(i);

            // first, we regroup the next compatible fences together in a composite blob :
            int j = i + 1;
            List<FenceVM> similarItems = new ArrayList<>();
            Link link = null;
            while (j < count) {
                FenceItem nextItem = list.get(j);
                if (item.link.equals(WHEN) && (link == null || link == nextItem.link)
                        || nextItem.link.equals(item.link)) {
                    similarItems.add(nextItem.fenceVM);
                    link = nextItem.link;
                    j++;
                } else break;
            }

            if (similarItems.isEmpty()) fenceToAccumulate = item.fenceVM;
            else {
                i = j - 1;
                similarItems.add(0, item.fenceVM);
                Operator op = link == Link.OR ? Operator.OR : Operator.AND;
                fenceToAccumulate = new CompositeFenceVM(similarItems, op);
            }

            // then we assemble with the existing result :

            if (completedFence == null) completedFence = fenceToAccumulate;
            else {
                Operator op = link == Link.OR ? Operator.OR : Operator.AND;
                completedFence = new CompositeFenceVM(Arrays.asList(completedFence, fenceToAccumulate), op);
            }

        }
        return completedFence;
    }

    /**
     * @return a new list, where there are only {@link Link#AND}, {@link Link#OR} & {@link Link#WHEN}
     * links, all the {@link Link#AND_NOT} & {@link Link#OR_NOT} see their fenceVMs wrapped in
     * {@link com.lostincontext.data.rules.NotFenceVM}s.
     */
    private List<FenceItem> getCleanedList() {
        List<FenceItem> cleanedItems = new ArrayList<>(items.size());

        for (FenceItem item : items) {
            cleanedItems.add(FenceItem.wrapNot(item));
        }
        return cleanedItems;
    }
}
