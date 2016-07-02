package com.lostincontext.googleapi;


import dagger.Component;

@Component(modules = AwarenessModule.class)
public interface AwarenessComponent {

    Awareness getGoogleApiRepo();

}
