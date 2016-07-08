package com.lostincontext.mainscreen;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.awareness.fence.FenceUpdateRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.lostincontext.awareness.Awareness;
import com.lostincontext.config.RuleConfiguration;
import com.lostincontext.model.rules.CompositeRuleDescription;
import com.lostincontext.model.rules.DetectedActivityRuleDescription;
import com.lostincontext.model.rules.HeadPhoneRuleDescription;
import com.lostincontext.model.rules.Rule;
import com.lostincontext.model.rules.RuleBuilder;
import com.lostincontext.model.rules.RuleDescription;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        RuleDescription runningDescription = new DetectedActivityRuleDescription(DetectedActivityRuleDescription.Type.IN_VEHICLE, DetectedActivityRuleDescription.State.DURING);
        List<RuleDescription> rules = new ArrayList<>();
        rules.add(headPhoneDescription);
        rules.add(runningDescription);

        RuleDescription compositeRuleDescription = new CompositeRuleDescription(rules, CompositeRuleDescription.Operator.AND);


        Rule compositeRule = compositeRuleDescription.visit(new RuleBuilder());


        builder.addFence(compositeRule.getName(), compositeRule.getFence(), view.getPendingIntent());
        awareness.updateFences(builder.build());

        RuleConfiguration ruleConfiguration = view.getRuleConfiguration();
        ruleConfiguration.clearAll();

        try {
            ruleConfiguration.serializeAndSave("compositeRule", compositeRuleDescription);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            final RuleDescription ruledescription = ruleConfiguration.loadAndDeserialize("compositeRule");
//            RuleBuilder ruleBuilder = new RuleBuilder();
//            final Rule rule = ruleBuilder.buildRule(ruledescription);

        } catch (IOException e) {
            e.printStackTrace();
        }


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


