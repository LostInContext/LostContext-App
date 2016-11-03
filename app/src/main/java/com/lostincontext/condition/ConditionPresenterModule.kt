package com.lostincontext.ruledetails

import android.os.Bundle
import com.lostincontext.awareness.AwarenessModule
import com.lostincontext.condition.ConditionContract
import dagger.Module
import dagger.Provides


@Module(includes = arrayOf(AwarenessModule::class))
class ConditionPresenterModule(private val view: ConditionContract.View,
                               private val icicle: Bundle?) {

    @Provides internal fun provideView(): ConditionContract.View = view

    @Provides internal fun provideBundle(): Bundle? = icicle

}
