package com.lostincontext.awareness


import android.util.Log

import com.google.android.gms.awareness.fence.FenceQueryRequest
import com.google.android.gms.awareness.fence.FenceQueryResult
import com.google.android.gms.awareness.fence.FenceUpdateRequest
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener
import com.google.android.gms.common.api.PendingResult
import com.google.android.gms.common.api.ResultCallbacks
import com.google.android.gms.common.api.Status
import com.lostincontext.commons.BaseActivity

import javax.inject.Inject


class Awareness
@Inject
constructor(private val googleApiClient: GoogleApiClient) {


    fun init(connectionCallbacks: ConnectionCallbacks,
             connectionFailedListener: OnConnectionFailedListener) {
        googleApiClient.registerConnectionCallbacks(connectionCallbacks)
        googleApiClient.registerConnectionFailedListener(connectionFailedListener)
        googleApiClient.connect()
    }

    @Inject
    fun setup(activity: BaseActivity) {
        activity.registerLifecycleCallbacks(lifecycleCallbacks)
    }

    fun updateFence(fenceUpdateRequest: FenceUpdateRequest): PendingResult<Status> {
        return com.google.android.gms.awareness.Awareness.FenceApi.updateFences(googleApiClient,
                fenceUpdateRequest)
    }

    fun queryFences(): PendingResult<FenceQueryResult> {
        return com.google.android.gms.awareness.Awareness.FenceApi.queryFences(googleApiClient,
                FenceQueryRequest.all())
    }

    fun deleteAllFences() {
        queryFences().setResultCallback(object : ResultCallbacks<FenceQueryResult>() {
            override fun onSuccess(fenceQueryResult: FenceQueryResult) {
                val fenceKeys = fenceQueryResult.fenceStateMap.fenceKeys
                Log.d(TAG, "onSuccess: number of keys : " + fenceKeys.size)
                for (fenceKey in fenceKeys) {
                    Log.d(TAG, "deleting key : " + fenceKey)
                    val builder = FenceUpdateRequest.Builder()
                    builder.removeFence(fenceKey)
                    updateFence(builder.build())
                }
            }

            override fun onFailure(status: Status) {
                Log.e(TAG, "deleteAllFences : onFailure: ")
            }
        })
    }

    //region LifecycleCallbacks

    private val lifecycleCallbacks = LifecycleCallbacks()

    private inner class LifecycleCallbacks : BaseActivity.ActivityLifecycleCallbacks {

        override fun onActivityCreated(activity: BaseActivity) {
        }

        override fun onActivityStarted(activity: BaseActivity) {
            if (googleApiClient.isConnecting || googleApiClient.isConnected) return
            googleApiClient.connect()
        }

        override fun onActivityResumed(activity: BaseActivity) {
        }

        override fun onActivityPaused(activity: BaseActivity) {
        }

        override fun onActivityStopped(activity: BaseActivity) {
            if (googleApiClient.isConnected || googleApiClient.isConnecting) {
                googleApiClient.disconnect()
            }
        }

        override fun onActivityDestroyed(activity: BaseActivity) {
            activity.unregisterLifecycleCallbacks(this)
        }
    }

    companion object {
        private val TAG = Awareness::class.java.simpleName
    }

    //endregion

}
