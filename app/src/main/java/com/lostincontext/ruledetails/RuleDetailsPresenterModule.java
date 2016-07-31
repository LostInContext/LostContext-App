package com.lostincontext.ruledetails;

import com.lostincontext.awareness.AwarenessModule;

import dagger.Module;
import dagger.Provides;


@Module(includes = {AwarenessModule.class})
public class RuleDetailsPresenterModule {

    private final RuleDetailsContract.View view;

    public RuleDetailsPresenterModule(RuleDetailsContract.View view) {
        this.view = view;
    }

    @Provides
    RuleDetailsContract.View provideMainScreenContractView() {
        return view;
    }
}
