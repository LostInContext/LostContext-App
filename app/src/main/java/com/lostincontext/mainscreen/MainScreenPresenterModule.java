package com.lostincontext.mainscreen;

import dagger.Module;
import dagger.Provides;


@Module
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


