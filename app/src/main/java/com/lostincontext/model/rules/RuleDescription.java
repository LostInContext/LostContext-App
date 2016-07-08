package com.lostincontext.model.rules;

/**
 * Created by syrinetrabelsi on 05/07/2016.
 */

public interface RuleDescription {

    Rule visit(RuleBuilder builder);

}
