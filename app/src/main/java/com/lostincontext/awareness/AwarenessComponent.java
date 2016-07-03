package com.lostincontext.awareness;


import dagger.Component;

@Component(modules = AwarenessModule.class)
public interface AwarenessComponent {

    Awareness getGoogleApiRepo();

}
