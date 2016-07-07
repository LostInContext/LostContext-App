package com.lostincontext.mainscreen;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.awareness.fence.FenceUpdateRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.lostincontext.awareness.Awareness;
import com.lostincontext.config.RuleConfiguration;
import com.lostincontext.rules.CompositeRuleDescription;
import com.lostincontext.rules.DetectedActivityRuleDescription;
import com.lostincontext.rules.HeadPhoneRuleDescription;
import com.lostincontext.rules.Rule;
import com.lostincontext.rules.RuleBuilder;
import com.lostincontext.rules.RuleDescription;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        ruleConfiguration.save("compositeRule", compositeRuleDescription.serialize(new Gson()));
        final Map<String, String> stringStringMap = ruleConfiguration.loadAll();
        for (String key :
                stringStringMap.keySet()) {
            final String object = stringStringMap.get(key);
            RuleDescription ruleDescription = new Gson().fromJson(object, RuleDescription.class);

            Log.i("jsonnew", new Gson().toJson(ruleDescription));

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


    @Override public void onConnected(@Nullable Bundle bundle) { }

    @Override public void onConnectionSuspended(int i) { }

    @Override public void onConnectionFailed(@NonNull ConnectionResult connectionResult) { }
}


