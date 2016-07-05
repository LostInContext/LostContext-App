package com.lostincontext.mainscreen;

import com.lostincontext.commons.FragmentScope;

import dagger.Component;

@FragmentScope
@Component(modules = MainScreenPresenterModule.class)
public interface MainScreenComponent {

    void inject(MainActivity activity);
}