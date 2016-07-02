package com.lostincontext.mainscreen;

import com.lostincontext.commons.FragmentScope;
import com.lostincontext.googleapi.AwarenessComponent;

import dagger.Component;

@FragmentScope
@Component(dependencies = AwarenessComponent.class, modules = MainScreenPresenterModule.class)
public interface MainScreenFragmentComponent {

    void inject(MainActivity activity);
}