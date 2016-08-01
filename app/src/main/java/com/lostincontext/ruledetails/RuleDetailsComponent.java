package com.lostincontext.ruledetails;

import com.lostincontext.application.ApplicationComponent;
import com.lostincontext.commons.FragmentScope;

import dagger.Component;


@FragmentScope
@Component(modules = RuleDetailsPresenterModule.class, dependencies = ApplicationComponent.class)
public interface RuleDetailsComponent {

    void inject(RuleDetailsActivity activity);

}