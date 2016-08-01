package com.lostincontext.awareness;


import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.awareness.fence.FenceQueryRequest;
import com.google.android.gms.awareness.fence.FenceQueryResult;
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

    public PendingResult<Status> updateFence(final FenceUpdateRequest fenceUpdateRequest) {
        return com.google.android.gms.awareness.Awareness.FenceApi.updateFences(googleApiClient,
                                                                                fenceUpdateRequest);
    }

    public PendingResult<FenceQueryResult> queryFences() {
        return com.google.android.gms.awareness.Awareness.FenceApi.queryFences(googleApiClient,
                                                                               FenceQueryRequest.all());
    }

    public void deleteAllFences() {
        queryFences().setResultCallback(new ResultCallbacks<FenceQueryResult>() {
            @Override public void onSuccess(@NonNull FenceQueryResult fenceQueryResult) {
                Set<String> fenceKeys = fenceQueryResult.getFenceStateMap().getFenceKeys();
                Log.d(TAG, "onSuccess: number of keys : " + fenceKeys.size());
                for (String fenceKey : fenceKeys) {
                    Log.d(TAG, "deleting key : " + fenceKey);
                    FenceUpdateRequest.Builder builder = new FenceUpdateRequest.Builder();
                    builder.removeFence(fenceKey);
                    updateFence(builder.build());
                }
            }

            @Override public void onFailure(@NonNull Status status) {
                Log.e(TAG, "deleteAllFences : onFailure: ");
            }
        });
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
