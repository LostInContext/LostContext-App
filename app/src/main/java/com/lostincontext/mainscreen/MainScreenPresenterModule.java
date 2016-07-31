package com.lostincontext.mainscreen;

import com.lostincontext.awareness.AwarenessModule;
import com.lostincontext.data.rules.repo.RulesRepositoryModule;

import dagger.Module;
import dagger.Provides;


@Module(includes = {AwarenessModule.class})
public class MainScreenPresenterModule {

    private final MainScreenContract.View view;

    public MainScreenPresenterModule(MainScreenContract.View view) {
        this.view = view;
    }

    @Provides
    MainScreenContract.View provideMainScreenContractView() {
        return view;
    }

}


