package com.lostincontext.mainscreen;

import com.lostincontext.commons.FragmentScope;
import com.lostincontext.data.rules.repo.RulesRepositoryComponent;

import dagger.Component;

@FragmentScope
@Component(modules = MainScreenPresenterModule.class, dependencies = RulesRepositoryComponent.class)
public interface MainScreenComponent {

    void inject(MainActivity activity);
}