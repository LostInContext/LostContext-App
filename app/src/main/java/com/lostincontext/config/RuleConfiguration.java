package com.lostincontext.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lostincontext.model.rules.RuleDescription;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by syrinetrabelsi on 06/07/2016.
 */

public class RuleConfiguration {
    private static final String TAG = RuleConfiguration.class.getSimpleName();

    private SharedPreferences preferences;
    private ObjectMapper objectMapper;

    public RuleConfiguration(Context context) {
        preferences = context.getSharedPreferences(context.getPackageName(), MODE_PRIVATE);
        objectMapper = new ObjectMapper();
    }

    public void serializeAndSave(String name, RuleDescription ruleDescription) throws JsonProcessingException {
        save(name, serialize(ruleDescription));
    }

    public RuleDescription loadAndDeserialize(String name) throws IOException {
        return deserialize(load(name));

    }


    private void save(String title, String json) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(title, json);
        editor.apply();

        Log.i(TAG, "saving: " + json);
    }

    private String load(String title) {
        final String data = preferences.getString(title, "");
        Log.i(TAG, "loading: " + data);
        return data;
    }

    public Map<String, String> loadAll() {
        Map<String, String> rules = new HashMap<>();
        Map<String, ?> keys = preferences.getAll();

        for (Map.Entry<String, ?> entry : keys.entrySet()) {
            rules.put(entry.getKey(), entry.getValue().toString());
            Log.d("map values", entry.getKey() + ": " +
                    entry.getValue().toString());
        }
        return rules;
    }

    public void clearAll() {
        preferences.edit()
                .clear()
                .apply();
    }


    //////////////////////////////////////////////////////////////////////////////////////
    // Serializer/deserialiser
    //////////////////////////////////////////////////////////////////////////////////////

    private String serialize(RuleDescription ruleDescription) throws JsonProcessingException {
        return objectMapper.writeValueAsString(ruleDescription);

    }

    private RuleDescription deserialize(String json) throws IOException {
        return objectMapper.readerFor(RuleDescription.class).readValue(json);
    }


}
