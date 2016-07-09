package com.lostincontext.data.rules.repo;

import android.content.SharedPreferences;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lostincontext.data.rules.FenceVM;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;


public class RulesRepository {

    private final static String RULES_KEY = "rules_Key_prefs";


    interface LoadTasksCallback {

        void onTasksLoaded(List<FenceVM> rules);

        void onTasksLoadFailure();
    }


    private static final String TAG = RulesRepository.class.getSimpleName();

    private final SharedPreferences preferences;
    private final ObjectMapper objectMapper;

    @Inject public RulesRepository(SharedPreferences preferences,
                                   ObjectMapper objectMapper) {
        this.preferences = preferences;
        this.objectMapper = objectMapper;
    }

    public void saveRule(String name, FenceVM fenceVM) {
        try {
            saveToPrefs(name, serialize(fenceVM));
            addToRulesList(name);
        } catch (JsonProcessingException e) {
            Log.e(TAG, "save: ", e);
        }
    }

    private FenceVM getRule(String name) throws IOException {
        return deserialize(loadFromPrefs(name));
    }


    public void getRules(LoadTasksCallback callback) {
        Set<String> rulesNames = getRulesNames();
        List<FenceVM> rules = new ArrayList<>(rulesNames.size());
        try {
            for (String ruleName : rulesNames) {
                FenceVM fenceVM = null;

                fenceVM = getRule(ruleName);

                rules.add(fenceVM);
            }
            callback.onTasksLoaded(rules);
        } catch (IOException e) {
            Log.e(TAG, "exception occurred : ", e);
            callback.onTasksLoadFailure();
        }


    }

    public void clearAllRules() {
        Set<String> rulesNames = getRulesNames();
        SharedPreferences.Editor editor = preferences.edit();
        for (String ruleName : rulesNames) {
            editor.remove(ruleName);
        }
        editor.remove(RULES_KEY);
        editor.apply();
    }

    //region rules manipulation

    private void addToRulesList(String name) {
        Set<String> rulesNames = getRulesNames();

        rulesNames.add(name);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(RULES_KEY, rulesNames);
        editor.apply();
    }

    private Set<String> getRulesNames() {
        Set<String> rulesNames = preferences.getStringSet(RULES_KEY, null);
        if (rulesNames == null) {
            rulesNames = new HashSet<>();
        } else {
            rulesNames = new HashSet<>(rulesNames);
        }
        return rulesNames;
    }

    //endregion


    //region SharedPreferences editor

    private void saveToPrefs(String title, String json) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(title, json);
        editor.apply();

        Log.i(TAG, "saving: " + json);
    }

    private String loadFromPrefs(String title) {
        final String data = preferences.getString(title, "");
        Log.i(TAG, "loading: " + data);
        return data;
    }

    //endregion

    //region Serializer / deserializer

    private String serialize(FenceVM fenceVM) throws JsonProcessingException {
        return objectMapper.writeValueAsString(fenceVM);

    }

    private FenceVM deserialize(String json) throws IOException {
        return objectMapper.readerFor(FenceVM.class).readValue(json);
    }
    //endregion

}
