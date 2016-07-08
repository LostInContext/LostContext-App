package com.lostincontext.data.rules;

import java.util.List;

public class CompositeRuleDescription implements RuleDescription {

    private List<RuleDescription> ruleDescriptions;
    private Operator operator;

    private CompositeRuleDescription() { }

    public CompositeRuleDescription(List<RuleDescription> ruleDescriptions, Operator operator) {
        this.ruleDescriptions = ruleDescriptions;
        this.operator = operator;
    }

    @Override
    public Rule visit(RuleBuilder builder) {
        return builder.buildCompositeRule(this);
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

    public void setRuleDescriptions(List<RuleDescription> ruleDescriptions) {
        this.ruleDescriptions = ruleDescriptions;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }
}
