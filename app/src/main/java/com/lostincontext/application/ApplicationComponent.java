package com.lostincontext.application;


import com.lostincontext.data.location.repo.LocationRepository;
import com.lostincontext.data.rules.repo.RulesRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    LostApplication getApplication();

    LocationRepository getLocationRepository();

    RulesRepository getRulesRepository();
}
