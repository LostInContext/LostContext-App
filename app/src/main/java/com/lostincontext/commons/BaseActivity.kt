package com.lostincontext.commons

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.MainThread
import android.support.v7.app.AppCompatActivity
import java.util.*


abstract class BaseActivity : AppCompatActivity() {

    private val lifecycleListeners = HashSet<ActivityLifecycleCallbacks>()


    interface ActivityLifecycleCallbacks {

        fun onActivityCreated(activity: BaseActivity)

        fun onActivityStarted(activity: BaseActivity)

        fun onActivityResumed(activity: BaseActivity)

        fun onActivityPaused(activity: BaseActivity)

        fun onActivityStopped(activity: BaseActivity)

        fun onActivityDestroyed(activity: BaseActivity)

    }

    @MainThread
    fun registerLifecycleCallbacks(listener: ActivityLifecycleCallbacks) {
        lifecycleListeners.add(listener)
    }

    @MainThread
    fun unregisterLifecycleCallbacks(listener: ActivityLifecycleCallbacks) {
        lifecycleListeners.remove(listener)
    }

    @CallSuper override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleListeners.forEach { it.onActivityCreated(this) }

    }

    @CallSuper override fun onStart() {
        super.onStart()
        lifecycleListeners.forEach { it.onActivityStarted(this) }
    }

    @CallSuper override fun onResume() {
        super.onResume()
        lifecycleListeners.forEach { it.onActivityResumed(this) }
    }

    @CallSuper override fun onPause() {
        lifecycleListeners.forEach { it.onActivityPaused(this) }
        super.onPause()
    }

    @CallSuper override fun onStop() {
        lifecycleListeners.forEach { it.onActivityStopped(this) }
        super.onStop()
    }

    @CallSuper override fun onDestroy() {
        lifecycleListeners.forEach { it.onActivityDestroyed(this) }
        super.onDestroy()
    }

}
