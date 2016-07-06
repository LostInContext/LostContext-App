package com.lostincontext.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.lostincontext.rules.RuleDescription;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by syrinetrabelsi on 06/07/2016.
 */

public class RuleConfiguration {
    private static final String TAG = RuleConfiguration.class.getSimpleName();

    private SharedPreferences preferences;

    public RuleConfiguration(Context context) {
        preferences = context.getSharedPreferences(context.getPackageName(), MODE_PRIVATE);
    }

    public void save(RuleDescription ruleDescription) {
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String toJson = gson.toJson(ruleDescription);
        editor.putString("test", toJson);
        editor.apply();

        Log.i(TAG, "saving: " + toJson);
    }

//    public RuleDescription load(String name) {
//        Gson gson = new Gson();
//        RuleDescription fromJson = gson.fromJson(preferences.getString(name, ""), RuleDescription.class);
//        Log.i(TAG, "loading: " + fromJson);
//
//        return fromJson;
//    }
}
