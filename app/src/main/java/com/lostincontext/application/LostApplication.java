package com.lostincontext.application;


import android.app.Application;

import com.lostincontext.data.rules.repo.DaggerRulesRepositoryComponent;
import com.lostincontext.data.rules.repo.RulesRepositoryComponent;
import com.lostincontext.data.rules.repo.RulesRepositoryModule;

public class LostApplication extends Application {


    private RulesRepositoryComponent rulesRepositoryComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        rulesRepositoryComponent = DaggerRulesRepositoryComponent.builder()
                .applicationModule(new ApplicationModule(getApplicationContext(), this))
                .rulesRepositoryModule(new RulesRepositoryModule()).build();
    }

    public RulesRepositoryComponent getRulesRepositoryComponent() {
        return rulesRepositoryComponent;
    }

}
