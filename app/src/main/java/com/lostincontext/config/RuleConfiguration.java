package com.lostincontext.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

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

    public void save(String title, String json) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(title, json);
        editor.apply();

        Log.i(TAG, "saving: " + json);
    }

    public String load(String title) {
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
}
