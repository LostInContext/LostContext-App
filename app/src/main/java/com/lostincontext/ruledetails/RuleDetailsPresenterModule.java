package com.lostincontext.ruledetails;

import com.lostincontext.awareness.AwarenessModule;
import com.lostincontext.data.location.repo.LocationRepositoryModule;
import com.lostincontext.data.rules.repo.RulesRepositoryModule;

import dagger.Module;
import dagger.Provides;


@Module(includes = {LocationRepositoryModule.class, RulesRepositoryModule.class, AwarenessModule.class})
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
