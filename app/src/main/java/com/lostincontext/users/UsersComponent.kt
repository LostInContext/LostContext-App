package com.lostincontext.users

import com.lostincontext.application.ApplicationComponent
import com.lostincontext.commons.FragmentScope
import dagger.Component


@FragmentScope
@Component(modules = arrayOf(UsersPresenterModule::class),
           dependencies = arrayOf(ApplicationComponent::class))
interface UsersComponent {

    fun inject(fragment: UsersFragment)

}