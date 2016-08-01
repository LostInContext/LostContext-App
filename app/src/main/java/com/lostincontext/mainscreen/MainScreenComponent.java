package com.lostincontext.mainscreen;

import com.lostincontext.application.ApplicationComponent;
import com.lostincontext.commons.FragmentScope;

import dagger.Component;

@FragmentScope
@Component(modules = MainScreenPresenterModule.class, dependencies = ApplicationComponent.class)
public interface MainScreenComponent {

    void inject(MainActivity activity);
}