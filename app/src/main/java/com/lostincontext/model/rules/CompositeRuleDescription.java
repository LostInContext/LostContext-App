package com.lostincontext.model.rules;

import com.google.gson.Gson;
import com.lostincontext.model.DataSerializer;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by syrinetrabelsi on 05/07/2016.
 */

public class CompositeRuleDescription implements RuleDescription<CompositeRuleDescription> {
    private final List<RuleDescription> ruleDescriptions;
    private final Operator operator;

    public CompositeRuleDescription(List<RuleDescription> ruleDescriptions, Operator operator) {
        this.ruleDescriptions = ruleDescriptions;
        this.operator = operator;
    }

    @Override
    public Rule visit(RuleBuilder builder) {
        return builder.buildCompositeRule(this);
    }

    @Override
    public String serialize(Gson gson) {
        return gson.toJson(this);
    }

    @Override
    public CompositeRuleDescription deserialize(Gson gson, String json) {
        return gson.fromJson(json, CompositeRuleDescription.class);
    }

    @Override
    public CompositeRuleDescription createInstance(Type type) {
        return null;
    }

    public enum Operator {
        AND,
        OR
    }

    public List<RuleDescription> getRuleDescriptions() {
        return ruleDescriptions;
    }


    public Operator getOperator() {
        return operator;
    }


}
