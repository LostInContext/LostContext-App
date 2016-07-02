package com.lostincontext.googleapi;

import android.support.v4.app.FragmentActivity;

import com.google.android.gms.awareness.Awareness;
import com.google.android.gms.common.api.GoogleApiClient;

import dagger.Module;
import dagger.Provides;

@Module
public class AwarenessModule {

    private FragmentActivity activity;

    public AwarenessModule(FragmentActivity activity) {
        this.activity = activity;
    }

    @Provides
    GoogleApiClient provideGoogleApi() {
        return new GoogleApiClient.Builder(activity)
                .addApi(Awareness.API)
                .build();
    }

    @Provides
    FragmentActivity provideFragmentActivity(){
        return activity;
    }
}



