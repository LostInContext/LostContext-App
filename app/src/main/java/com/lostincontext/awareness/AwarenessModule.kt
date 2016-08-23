package com.lostincontext.awareness

import com.google.android.gms.awareness.Awareness
import com.google.android.gms.common.api.GoogleApiClient
import com.lostincontext.commons.BaseActivity

import dagger.Module
import dagger.Provides

@Module
class AwarenessModule(private val activity: BaseActivity) {

    @Provides
    internal fun provideGoogleApi(): GoogleApiClient {
        return GoogleApiClient.Builder(activity).addApi(Awareness.API).build()
    }

    @Provides
    internal fun provideBaseActivity(): BaseActivity {
        return activity
    }
}



