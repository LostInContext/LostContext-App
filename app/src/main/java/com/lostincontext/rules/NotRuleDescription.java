package com.lostincontext.rules;

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
}
