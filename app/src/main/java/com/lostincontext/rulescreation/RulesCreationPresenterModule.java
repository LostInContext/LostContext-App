package com.lostincontext.rulescreation;

import com.lostincontext.data.location.repo.LocationRepositoryModule;
import com.lostincontext.data.playlist.repo.PlaylistRepositoryModule;

import dagger.Module;
import dagger.Provides;

@Module(includes = {LocationRepositoryModule.class, PlaylistRepositoryModule.class})
public class RulesCreationPresenterModule {
    private final RulesCreationContract.View view;

    public RulesCreationPresenterModule(RulesCreationContract.View view) {
        this.view = view;
    }

    @Provides
    RulesCreationContract.View provideMainScreenContractView() {
        return view;
    }
}
