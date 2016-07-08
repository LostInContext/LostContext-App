package com.lostincontext.data.rules;


public class NotRuleDescription implements RuleDescription {
    private RuleDescription rule;

    private NotRuleDescription() { }

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
