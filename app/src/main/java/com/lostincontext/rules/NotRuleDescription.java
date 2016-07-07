package com.lostincontext.rules;

import com.google.gson.Gson;

/**
 * Created by syrinetrabelsi on 05/07/2016.
 */

public class NotRuleDescription implements RuleDescription {
    private final RuleDescription rule;

    public NotRuleDescription(RuleDescription rule) {
        this.rule = rule;
    }

    public RuleDescription getRule() {
        return rule;
    }


    @Override public Rule visit(RuleBuilder builder) {
        return builder.buildRule(getRule());
    }

    @Override public String serialize(Gson gson) {
        return gson.toJson(this);
    }

    @Override public NotRuleDescription deserialize(Gson gson, String json) {
        return gson.fromJson(json, NotRuleDescription.class);
    }
}
