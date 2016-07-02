package com.lostincontext.mainscreen;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.lostincontext.googleapi.Awareness;

import javax.inject.Inject;


public class MainScreenPresenter implements MainScreenContract.Presenter,
                                            GoogleApiClient.ConnectionCallbacks,
                                            GoogleApiClient.OnConnectionFailedListener {

    private MainScreenContract.View view;

    private Awareness awareness;


    @Override
    public void start() { }

    @Inject
    public MainScreenPresenter(MainScreenContract.View view,
                               Awareness awareness) {
        this.view = view;
        this.awareness = awareness;
    }

    @Inject
    void setupListeners() {
        view.setPresenter(this);
        awareness.init(this,
                       this);
    }


    @Override public void onConnected(@Nullable Bundle bundle) {

    }

    @Override public void onConnectionSuspended(int i) {

    }

    @Override public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}


