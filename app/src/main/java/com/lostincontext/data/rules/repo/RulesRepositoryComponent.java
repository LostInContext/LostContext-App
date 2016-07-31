package com.lostincontext.data.rules.repo;

import com.lostincontext.application.ApplicationComponent;
import com.lostincontext.application.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RulesRepositoryModule.class, ApplicationModule.class})
public interface RulesRepositoryComponent extends ApplicationComponent {

    RulesRepository getRulesRepository();
}