package com.lostincontext.data.location.repo;

import com.lostincontext.application.ApplicationComponent;
import com.lostincontext.application.ApplicationModule;
import com.lostincontext.data.rules.repo.RulesRepository;
import com.lostincontext.data.rules.repo.RulesRepositoryModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {LocationRepositoryModule.class, ApplicationModule.class})
public interface LocationRepositoryComponent extends ApplicationComponent {

    LocationRepository getLocationRepository();
}