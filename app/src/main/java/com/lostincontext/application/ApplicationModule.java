package com.lostincontext.application;


import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module public final class ApplicationModule {

    private final Context context;
    private final LostApplication lostApplication;

    ApplicationModule(Context context, LostApplication lostApplication) {
        this.context = context;
        this.lostApplication = lostApplication;
    }

    @Provides
    Context provideContext() {
        return context;
    }

    @Provides
    LostApplication provideApplication() {
        return lostApplication;
    }
}