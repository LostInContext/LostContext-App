package com.lostincontext.googleapi;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.common.base.Objects;

import javax.inject.Inject;

public class Awareness {

    GoogleApiClient googleApiClient;

    FragmentActivity activity;

    @Inject
    public Awareness(GoogleApiClient googleApiClient,
                     FragmentActivity activity) {
        this.googleApiClient = googleApiClient;
        this.activity = activity;
    }


    public void init(ConnectionCallbacks connectionCallbacks,
                     OnConnectionFailedListener connectionFailedListener) {
        googleApiClient.registerConnectionCallbacks(connectionCallbacks);
        googleApiClient.registerConnectionFailedListener(connectionFailedListener);
        googleApiClient.connect();
        activity.getApplication().registerActivityLifecycleCallbacks(lifecycleCallback);
    }

    public GoogleApiClient getGoogleApiClient() {
        return googleApiClient;
    }

    //region LifecycleCallbacks
    private LifecycleCallback lifecycleCallback = new LifecycleCallback();

    public class LifecycleCallback implements Application.ActivityLifecycleCallbacks {

        @Override public void onActivityCreated(Activity activity, Bundle bundle) { }

        @Override public void onActivityStarted(Activity activity) {
            if (activity == null || !Objects.equal(Awareness.this.activity, activity)) return;
            if (googleApiClient.isConnecting() || googleApiClient.isConnected()) return;
            googleApiClient.connect();
        }

        @Override public void onActivityResumed(Activity activity) { }

        @Override public void onActivityPaused(Activity activity) { }

        @Override public void onActivityStopped(Activity activity) {
            if (activity == null || !Objects.equal(Awareness.this.activity, activity)) return;

            if (googleApiClient.isConnected() || googleApiClient.isConnecting()) {
                googleApiClient.disconnect();
            }

        }

        @Override public void onActivitySaveInstanceState(Activity activity, Bundle bundle) { }

        @Override public void onActivityDestroyed(Activity activity) {
            activity.getApplication().unregisterActivityLifecycleCallbacks(this);
        }

    }
    //endregion

}
