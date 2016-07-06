package com.lostincontext.mainscreen;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.awareness.fence.FenceUpdateRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.lostincontext.awareness.Awareness;
import com.lostincontext.rules.HeadPhoneRuleDescription;
import com.lostincontext.rules.Rule;
import com.lostincontext.rules.RuleBuilder;
import com.lostincontext.rules.RuleDescription;

import javax.inject.Inject;


public class MainScreenPresenter implements MainScreenContract.Presenter,
                                            GoogleApiClient.ConnectionCallbacks,
                                            GoogleApiClient.OnConnectionFailedListener {

    private MainScreenContract.View view;

    private Awareness awareness;


    @Override
    public void start() {
        // POC test of how to register a fence
        FenceUpdateRequest.Builder builder = new FenceUpdateRequest.Builder();
        RuleDescription headPhoneDescription = new HeadPhoneRuleDescription(HeadPhoneRuleDescription.State.PLUGGED_IN);
        Rule headphone = headPhoneDescription.visit(new RuleBuilder());
        //  Rule running = RuleBuilder.duringRunning();
        //  Rule rule = RuleBuilder.and(headphone, running);
        builder.addFence(headphone.getName(), headphone.getFence(), view.getPendingIntent());
        awareness.updateFences(builder.build());
    }

    @Inject
    public MainScreenPresenter(MainScreenContract.View view,
                               Awareness awareness) {
        this.view = view;
        this.awareness = awareness;
    }

    @Inject
    void setup() {
        view.setPresenter(this);
        awareness.init(this,
                       this);
    }
    
    
    //region callbacks

    @Override public void onFabClicked() {
       view.openPlaylistsScreen();
        
    }


    //endregion


    @Override public void onConnected(@Nullable Bundle bundle) { }

    @Override public void onConnectionSuspended(int i) { }

    @Override public void onConnectionFailed(@NonNull ConnectionResult connectionResult) { }
}


