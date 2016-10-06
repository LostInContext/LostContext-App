package com.lostincontext.rulecreate

import android.os.Bundle
import com.lostincontext.awareness.AwarenessModule
import dagger.Module
import dagger.Provides

@Module(includes = arrayOf(AwarenessModule::class))
class RuleCreatePresenterModule(private val view: RuleCreateContract.View,
                                private val icicle: Bundle?) {
    @Provides internal fun provideView(): RuleCreateContract.View = view

    @Provides internal fun provideBundle(): Bundle? = icicle

}