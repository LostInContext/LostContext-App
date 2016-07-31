package com.lostincontext.application;


import dagger.Component;

@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    //Exposes Application to any component which depends on this
    LostApplication getApplication();
}