package com.lostincontext.mainscreen;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.awareness.fence.FenceUpdateRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.lostincontext.awareness.Awareness;
import com.lostincontext.data.playlist.DataPlaylist;
import com.lostincontext.data.playlist.Playlist;
import com.lostincontext.data.rules.CompositeFenceVM;
import com.lostincontext.data.rules.DetectedActivityFenceVM;
import com.lostincontext.data.rules.FenceBuilder;
import com.lostincontext.data.rules.FenceVM;
import com.lostincontext.data.rules.HeadphoneFenceVM;
import com.lostincontext.data.rules.Rule;
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
        final FenceUpdateRequest.Builder builder = new FenceUpdateRequest.Builder();

        FenceVM headPhoneDescription = new HeadphoneFenceVM(HeadphoneFenceVM.State.PLUGGED_IN);
//        FenceVM runningDescription = new DetectedActivityFenceVM(DetectedActivityFenceVM.Type.IN_VEHICLE, DetectedActivityFenceVM.State.DURING);
//        List<FenceVM> rules = new ArrayList<>();
//        rules.add(headPhoneDescription);
//        rules.add(runningDescription);

//        FenceVM compositeFenceVM = new CompositeFenceVM(rules, CompositeFenceVM.Operator.AND);


        //  Rule compositeRule = compositeFenceVM.build(new FenceBuilder());



        rulesRepository.clearAllRules();

        List<Playlist> playlists = DataPlaylist.getPlaylists();

        rulesRepository.saveRule(new Rule("test", headPhoneDescription, playlists.get(0)));
        rulesRepository.saveRule(new Rule("test2", headPhoneDescription, playlists.get(1)));


        rulesRepository.getRules(new RulesRepository.LoadTasksCallback() {
            @Override public void onTasksLoaded(List<Rule> rules) {
                view.setRules(rules);
                final Rule rule = rules.get(0);
                builder
                        .addFence(rule.getName(), rule.getFenceVM()
                                .build(new FenceBuilder()), view.getPendingIntent(rule.getPlaylist()));


                awareness.updateFences(builder.build());

            }

            @Override public void onTasksLoadFailure() {

            }
        });
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

    @Override public void onRefreshButtonClick() {

    }
}


