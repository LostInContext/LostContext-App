package com.lostincontext.awareness;

import android.util.Log;

import com.google.android.gms.awareness.fence.FenceQueryRequest;
import com.google.android.gms.awareness.fence.FenceUpdateRequest;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.lostincontext.commons.BaseActivity;

import javax.inject.Inject;


public class Awareness {

    private GoogleApiClient googleApiClient;


    @Inject
    public Awareness(GoogleApiClient googleApiClient) {
        this.googleApiClient = googleApiClient;
    }


    public void init(ConnectionCallbacks connectionCallbacks,
                     OnConnectionFailedListener connectionFailedListener) {
        googleApiClient.registerConnectionCallbacks(connectionCallbacks);
        googleApiClient.registerConnectionFailedListener(connectionFailedListener);
        googleApiClient.connect();
    }

    @Inject
    public void setup(BaseActivity activity) {
        activity.registerListener(lifecycleCallbacks);
        Log.d("fbl", "setup : " + this.hashCode()); // TODO DO NOT COMMIT

    }

    public void updateFences(FenceUpdateRequest fenceUpdateRequest) {
        com.google.android.gms.awareness.Awareness.FenceApi.updateFences(googleApiClient,
                                                                         fenceUpdateRequest);
    }

    public void queryFences(FenceQueryRequest fenceQueryRequest) {
        com.google.android.gms.awareness.Awareness.FenceApi.queryFences(googleApiClient,
                                                                        fenceQueryRequest);
    }

    //region LifecycleCallbacks

    private LifecycleCallbacks lifecycleCallbacks = new LifecycleCallbacks();

    private class LifecycleCallbacks implements BaseActivity.ActivityLifecycleCallbacks {

        @Override public void onActivityCreated(BaseActivity activity) { }

        @Override public void onActivityStarted(BaseActivity activity) {
            if (googleApiClient.isConnecting() || googleApiClient.isConnected()) return;
            googleApiClient.connect();
        }

        @Override public void onActivityResumed(BaseActivity activity) { }

        @Override public void onActivityPaused(BaseActivity activity) { }

        @Override public void onActivityStopped(BaseActivity activity) {
            if (googleApiClient.isConnected() || googleApiClient.isConnecting()) {
                googleApiClient.disconnect();
            }
        }

        @Override public void onActivityDestroyed(BaseActivity activity) {
            activity.unregisterListener(this);
        }
    }

    //endregion

}
