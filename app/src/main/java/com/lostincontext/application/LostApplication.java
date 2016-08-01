package com.lostincontext.application;

import android.app.Application;
import android.support.annotation.NonNull;


public class LostApplication extends Application {


    private ApplicationComponent applicationComponent;

    @Override public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();

    }


    @NonNull
    public ApplicationComponent getAppComponent() {
        return applicationComponent;
    }

}
