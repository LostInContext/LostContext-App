package com.lostincontext.mainscreen;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.awareness.fence.FenceUpdateRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.lostincontext.awareness.Awareness;
import com.lostincontext.data.DataPlaylist;
import com.lostincontext.data.Playlist;
import com.lostincontext.data.rules.CompositeFenceVM;
import com.lostincontext.data.rules.DetectedActivityFenceVM;
import com.lostincontext.data.rules.FenceVM;
import com.lostincontext.data.rules.HeadphoneFenceVM;
import com.lostincontext.data.rules.repo.RulesRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class MainScreenPresenter implements MainScreenContract.Presenter,
                                            GoogleApiClient.ConnectionCallbacks,
                                            GoogleApiClient.OnConnectionFailedListener {

    private MainScreenContract.View view;

    private Awareness awareness;

    private RulesRepository rulesRepository;


    @Override
    public void start() {
        // POC test of how to register a fence
        FenceUpdateRequest.Builder builder = new FenceUpdateRequest.Builder();

        FenceVM headPhoneDescription = new HeadphoneFenceVM(HeadphoneFenceVM.State.PLUGGED_IN);
        FenceVM runningDescription = new DetectedActivityFenceVM(DetectedActivityFenceVM.Type.IN_VEHICLE, DetectedActivityFenceVM.State.DURING);
        List<FenceVM> rules = new ArrayList<>();
        rules.add(headPhoneDescription);
        rules.add(runningDescription);

        FenceVM compositeFenceVM = new CompositeFenceVM(rules, CompositeFenceVM.Operator.AND);


        //  Rule compositeRule = compositeFenceVM.build(new FenceBuilder());


        //  builder.addFence(compositeRule.getName(), compositeRule.getFence(), view.getPendingIntent());
        awareness.updateFences(builder.build());

        rulesRepository.clearAllRules();

        List<Playlist> playlists = DataPlaylist.getPlaylists();

        rulesRepository.saveRule("compositeRule", compositeFenceVM);
        //final FenceVM ruledescription = rulesRepository.loadRule("compositeRule");
    }

    @Inject
    public MainScreenPresenter(MainScreenContract.View view,
                               Awareness awareness,
                               RulesRepository rulesRepository) {
        this.view = view;
        this.awareness = awareness;
        this.rulesRepository = rulesRepository;
    }

    @Inject
    void setup() {
        view.setPresenter(this);
        awareness.init(this,
                       this);
    }


    //region callbacks

    @Override public void onFabClicked() {
        view.openRuleCreationScreen();
    }


    //endregion


    @Override public void onConnected(@Nullable Bundle bundle) { }

    @Override public void onConnectionSuspended(int i) { }

    @Override public void onConnectionFailed(@NonNull ConnectionResult connectionResult) { }
}


