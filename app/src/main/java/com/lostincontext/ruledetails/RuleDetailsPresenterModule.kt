package com.lostincontext.ruledetails

import com.lostincontext.awareness.AwarenessModule

import dagger.Module
import dagger.Provides


@Module(includes = arrayOf(AwarenessModule::class))
class RuleDetailsPresenterModule(private val view: RuleDetailsContract.View) {

    @Provides
    internal fun provideMainScreenContractView(): RuleDetailsContract.View {
        return view
    }
}
