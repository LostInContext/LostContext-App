package com.lostincontext.rules;

import com.lostincontext.model.DataSerializer;

/**
 * Created by syrinetrabelsi on 05/07/2016.
 */

public interface RuleDescription<T> extends DataSerializer<T> {

    Rule visit(RuleBuilder builder);

}
