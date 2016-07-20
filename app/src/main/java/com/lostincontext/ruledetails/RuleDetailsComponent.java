package com.lostincontext.ruledetails;

import com.lostincontext.commons.FragmentScope;

import dagger.Component;


@FragmentScope
@Component(modules = RuleDetailsPresenterModule.class)
public interface RuleDetailsComponent {

    void inject(RuleDetailsActivity activity);

}