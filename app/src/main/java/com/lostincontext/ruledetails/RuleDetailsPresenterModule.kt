package com.lostincontext.ruledetails

import android.os.Bundle
import com.lostincontext.awareness.AwarenessModule

import dagger.Module
import dagger.Provides


@Module(includes = arrayOf(AwarenessModule::class))
class RuleDetailsPresenterModule(private val view: RuleDetailsContract.View,
                                 private val icicle: Bundle?) {

    @Provides internal fun provideView(): RuleDetailsContract.View = view

    @Provides internal fun provideBundle(): Bundle? = icicle

}
