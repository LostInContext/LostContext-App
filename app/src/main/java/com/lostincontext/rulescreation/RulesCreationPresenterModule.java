package com.lostincontext.rulescreation;

import dagger.Module;
import dagger.Provides;

@Module
public class RulesCreationPresenterModule {
    private final RulesCreationContract.View view;

    public RulesCreationPresenterModule(RulesCreationContract.View view) {
        this.view = view;
    }

    @Provides
    RulesCreationContract.View provideMainScreenContractView() {
        return view;
    }
}
