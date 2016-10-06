package com.lostincontext.rulecreate

import com.lostincontext.application.ApplicationComponent
import com.lostincontext.commons.FragmentScope
import dagger.Component

@FragmentScope
@Component(modules = arrayOf(RuleCreatePresenterModule::class),
           dependencies = arrayOf(ApplicationComponent::class))
interface RuleCreateComponent {

    fun inject(fragment: RuleCreateFragment)

}