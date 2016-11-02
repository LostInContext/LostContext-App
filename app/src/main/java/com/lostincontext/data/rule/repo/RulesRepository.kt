package com.lostincontext.data.rule.repo

import android.content.SharedPreferences
import android.util.Log
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.lostincontext.data.rule.Rule
import com.lostincontext.utils.logD
import java.io.IOException
import java.util.*
import javax.inject.Inject
import javax.inject.Named


class RulesRepository
@Inject constructor(@Named("rules") private val preferences: SharedPreferences,
                    private val objectMapper: ObjectMapper) {


    interface LoadTasksCallback {

        fun onTasksLoaded(rules: List<Rule>)

        fun onTasksLoadFailure()
    }

    fun saveRule(rule: Rule) {
        try {
            val name: String = rule.key
            saveToPrefs(name, serialize(rule))
            addToRulesList(name)
        } catch (e: JsonProcessingException) {
            Log.e(TAG, "save: ", e)
        }

    }

    fun deleteRule(rule: Rule) {
        try {
            val key: String = rule.key

            removeFromRulesList(key)
            deleteFromPrefs(key)
        } catch (e: JsonProcessingException) {
            Log.e(TAG, "delete: ", e)
        }
    }

    @Throws(IOException::class)
    fun getRule(name: String): Rule = deserialize(loadFromPrefs(name))


    fun getRules(callback: LoadTasksCallback) {
        val rulesNames = getRuleNames()
        val rules = ArrayList<Rule>(rulesNames.size)
        try {
            for (ruleName in rulesNames) {
                val rule: Rule

                rule = getRule(ruleName)

                rules.add(rule)
            }
            callback.onTasksLoaded(rules)
        } catch (e: IOException) {
            Log.e(TAG, "exception occurred : ", e)
            callback.onTasksLoadFailure()
        }


    }

    fun clearAllRules() {
        val rulesNames = getRuleNames()
        val editor = preferences.edit()
        for (ruleName in rulesNames) {
            editor.remove(ruleName)
        }
        editor.remove(RULES_KEY)
        editor.apply()
    }

    //region rules manipulation

    private fun addToRulesList(name: String) {
        val rulesNames = getRuleNames()

        rulesNames.add(name)

        val editor = preferences.edit()
        editor.putStringSet(RULES_KEY, rulesNames)
        editor.apply()
    }

    private fun removeFromRulesList(key: String) {
        val rulesNames = getRuleNames()

        rulesNames.remove(key)

        val editor = preferences.edit()
        editor.putStringSet(RULES_KEY, rulesNames)
        editor.apply()
    }


    fun getRuleNames() : MutableSet<String> {
        var rulesNames = preferences.getStringSet(RULES_KEY, null)
        if (rulesNames == null) {
            rulesNames = HashSet<String>()
        } else {
            rulesNames = HashSet(rulesNames)
        }
        return rulesNames
    }

    //endregion


    //region SharedPreferences editor

    private fun saveToPrefs(title: String, json: String) {
        val editor = preferences.edit()
        editor.putString(title, json)
        editor.apply()

        logD(TAG) { "saving : $json" }
    }

    private fun deleteFromPrefs(key: String) {
        val editor = preferences.edit()
        editor.remove(key)
        editor.apply()

        logD(TAG) { "deletion : $key" }

    }

    private fun loadFromPrefs(title: String): String {
        val data = preferences.getString(title, "")
        Log.i(TAG, "loading: " + data!!)
        return data
    }

    //endregion

    //region Serializer / deserializer

    @Throws(JsonProcessingException::class)
    private fun serialize(rule: Rule): String {
        return objectMapper.writeValueAsString(rule)

    }

    @Throws(IOException::class)
    private fun deserialize(json: String): Rule {
        return objectMapper.readerFor(Rule::class.java).readValue<Rule>(json)
    }

    companion object {

        private val RULES_KEY = "rules_Key_prefs"

        private val TAG = RulesRepository::class.java.simpleName
    }
    //endregion

}
