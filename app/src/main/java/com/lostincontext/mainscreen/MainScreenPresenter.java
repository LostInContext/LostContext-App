package com.lostincontext.mainscreen;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.lostincontext.awareness.Awareness;
import com.lostincontext.data.rules.Rule;
import com.lostincontext.data.rules.repo.RulesRepository;

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
        rulesRepository.getRules(new RulesRepository.LoadTasksCallback() {
            @Override public void onTasksLoaded(List<Rule> rules) {
                view.setRules(rules);

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


