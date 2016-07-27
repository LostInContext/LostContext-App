package com.lostincontext.awareness;


import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.awareness.fence.FenceQueryRequest;
import com.google.android.gms.awareness.fence.FenceQueryResult;
import com.google.android.gms.awareness.fence.FenceStateMap;
import com.google.android.gms.awareness.fence.FenceUpdateRequest;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.Status;
import com.lostincontext.commons.BaseActivity;

import java.util.Set;

import javax.inject.Inject;


public class Awareness {

    private GoogleApiClient googleApiClient;
    private static final String TAG = Awareness.class.getSimpleName();


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
        activity.registerLifecycleCallbacks(lifecycleCallbacks);
    }

    public void updateFences(final FenceUpdateRequest fenceUpdateRequest) {
        com.google.android.gms.awareness.Awareness.FenceApi.updateFences(googleApiClient,
                                                                         fenceUpdateRequest).setResultCallback(new ResultCallbacks<Status>() {
            @Override public void onSuccess(@NonNull Status status) {
                Log.i(TAG, "Fence was successfully registered.");
                final PendingResult<FenceQueryResult> fenceQueryResultPendingResult = queryFences();
                fenceQueryResultPendingResult.setResultCallback(new ResultCallbacks<FenceQueryResult>() {
                    @Override public void onSuccess(@NonNull FenceQueryResult fenceQueryResult) {
                        final FenceStateMap fenceStateMap = fenceQueryResult.getFenceStateMap();
                        final Set<String> fenceKeys = fenceStateMap.getFenceKeys();
                        for (String fenceKey : fenceKeys) {
                            Log.i(TAG, "fenceKey: " + fenceKey);
                        }
                    }

                    @Override public void onFailure(@NonNull Status status) {

                    }
                });
            }

            @Override public void onFailure(@NonNull Status status) {

            }
        });
    }

    public PendingResult<FenceQueryResult> queryFences() {
        return com.google.android.gms.awareness.Awareness.FenceApi.queryFences(googleApiClient,
                                                                               FenceQueryRequest.all());
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
            activity.unregisterLifecycleCallbacks(this);
        }
    }

    //endregion

}
