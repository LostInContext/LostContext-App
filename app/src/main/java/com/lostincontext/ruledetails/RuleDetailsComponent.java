package com.lostincontext.ruledetails;

import com.lostincontext.commons.FragmentScope;
import com.lostincontext.data.location.repo.LocationRepositoryComponent;
import com.lostincontext.data.rules.repo.RulesRepositoryComponent;

import dagger.Component;


@FragmentScope
@Component(modules = RuleDetailsPresenterModule.class,
        dependencies = {RulesRepositoryComponent.class, LocationRepositoryComponent.class})
public interface RuleDetailsComponent {

    void inject(RuleDetailsActivity activity);

}