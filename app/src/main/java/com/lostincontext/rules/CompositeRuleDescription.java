package com.lostincontext.rules;

import java.util.List;

/**
 * Created by syrinetrabelsi on 05/07/2016.
 */

public class CompositeRuleDescription extends RuleDescription {
    private List<RuleDescription> ruleDescriptions;
    private Operator operator;

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
