package com.lostincontext.ruledetails;

import dagger.Module;
import dagger.Provides;


@Module
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
