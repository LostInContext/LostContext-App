package com.lostincontext.rulescreation;

import com.lostincontext.commons.FragmentScope;

import dagger.Component;

@FragmentScope
@Component(modules = RulesCreationPresenterModule.class)
public interface RulesCreationComponent {

    void inject(RulesCreationActivity activity);

}
