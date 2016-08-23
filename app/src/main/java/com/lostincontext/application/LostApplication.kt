package com.lostincontext.application

import android.app.Application


class LostApplication : Application() {


    lateinit var appComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()

    }

}
