package com.lostincontext.ruledetails

import com.lostincontext.application.ApplicationComponent
import com.lostincontext.commons.FragmentScope

import dagger.Component


@FragmentScope
@Component(modules = arrayOf(RuleDetailsPresenterModule::class),
           dependencies = arrayOf(ApplicationComponent::class))
interface RuleDetailsComponent {

    fun inject(activity: RuleDetailsActivity)

}