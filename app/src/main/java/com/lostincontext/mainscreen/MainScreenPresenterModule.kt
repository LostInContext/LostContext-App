package com.lostincontext.mainscreen

import com.lostincontext.awareness.AwarenessModule

import dagger.Module
import dagger.Provides


@Module(includes = arrayOf(AwarenessModule::class))
class MainScreenPresenterModule(private val view: MainScreenContract.View) {

    @Provides
    internal fun provideMainScreenContractView(): MainScreenContract.View {
        return view
    }

}


