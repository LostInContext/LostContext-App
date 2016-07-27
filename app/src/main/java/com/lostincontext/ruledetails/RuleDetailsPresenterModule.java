package com.lostincontext.ruledetails;

import com.lostincontext.data.location.repo.LocationRepositoryModule;

import dagger.Module;
import dagger.Provides;


@Module(includes = {LocationRepositoryModule.class})
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
