package com.lostincontext.users

import android.os.Bundle
import com.lostincontext.awareness.AwarenessModule
import dagger.Module
import dagger.Provides


@Module(includes = arrayOf(AwarenessModule::class))
class UsersPresenterModule(private val view: UsersContract.View,
                           private val icicle: Bundle?) {

    @Provides internal fun provideView(): UsersContract.View = view

    @Provides internal fun provideBundle(): Bundle? = icicle

}
