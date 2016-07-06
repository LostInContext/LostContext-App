package com.lostincontext.rules;

import com.google.android.gms.awareness.fence.AwarenessFence;

/**
 * Created by syrinetrabelsi on 05/07/2016.
 */

public interface RuleDescription {

    Rule visit(RuleBuilder builder);

}
