package com.lostincontext.condition

import com.lostincontext.application.ApplicationComponent
import com.lostincontext.commons.FragmentScope
import com.lostincontext.condition.ConditionFragment
import com.lostincontext.ruledetails.ConditionPresenterModule

import dagger.Component

@FragmentScope
@Component(modules = arrayOf(ConditionPresenterModule::class),
           dependencies = arrayOf(ApplicationComponent::class))
interface ConditionComponent {

    fun inject(fragment: ConditionFragment)
}