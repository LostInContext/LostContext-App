package com.lostincontext.awareness;

import com.google.android.gms.awareness.Awareness;
import com.google.android.gms.common.api.GoogleApiClient;
import com.lostincontext.commons.BaseActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class AwarenessModule {

    private BaseActivity activity;

    public AwarenessModule(BaseActivity activity) {
        this.activity = activity;
    }

    @Provides
    GoogleApiClient provideGoogleApi() {
        return new GoogleApiClient.Builder(activity)
                .addApi(Awareness.API)
                .build();
    }

    @Provides
    BaseActivity provideBaseActivity() {
        return activity;
    }
}



