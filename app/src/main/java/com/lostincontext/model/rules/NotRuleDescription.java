package com.lostincontext.model.rules;

/**
 * Created by syrinetrabelsi on 05/07/2016.
 */

public class NotRuleDescription extends RuleDescription {
    private  RuleDescription rule;

    public NotRuleDescription() {
    }

    public NotRuleDescription(RuleDescription rule) {
        this.rule = rule;
    }

    public RuleDescription getRule() {
        return rule;
    }


    @Override public Rule visit(RuleBuilder builder) {
        return builder.buildRule(getRule());
    }

    public void setRule(RuleDescription rule) {
        this.rule = rule;
    }
}
