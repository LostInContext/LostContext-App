package com.lostincontext.commons

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.MainThread
import android.support.v7.app.AppCompatActivity
import java.util.*


abstract class BaseActivity : AppCompatActivity() {

    private val lifecycleCallbacks = ArrayList<ActivityLifecycleCallbacks>()


    interface ActivityLifecycleCallbacks {

        fun onActivityCreated(activity: BaseActivity)

        fun onActivityStarted(activity: BaseActivity)

        fun onActivityResumed(activity: BaseActivity)

        fun onActivityPaused(activity: BaseActivity)

        fun onActivityStopped(activity: BaseActivity)

        fun onActivityDestroyed(activity: BaseActivity)

    }

    @MainThread fun registerLifecycleCallbacks(callback: ActivityLifecycleCallbacks) {
        lifecycleCallbacks.add(callback)
    }

    @MainThread fun registerLifecycleCallbacks(callbacks: List<ActivityLifecycleCallbacks>) {
        lifecycleCallbacks.addAll(callbacks)
    }

    @MainThread
    fun unregisterLifecycleCallbacks(callback: ActivityLifecycleCallbacks) {
        lifecycleCallbacks.remove(callback)
    }

    @CallSuper override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        invokeListeners({ it.onActivityCreated(this) })
    }


    @CallSuper override fun onStart() {
        super.onStart()
        invokeListeners({ it.onActivityStarted(this) })
    }

    @CallSuper override fun onResume() {
        super.onResume()
        invokeListeners({ it.onActivityResumed(this) })
    }

    @CallSuper override fun onPause() {
        invokeListeners({ it.onActivityPaused(this) })
        super.onPause()
    }

    @CallSuper override fun onStop() {
        invokeListeners({ it.onActivityStopped(this) })
        super.onStop()
    }

    @CallSuper override fun onDestroy() {
        invokeListeners({ it.onActivityDestroyed(this) })
        super.onDestroy()
    }

    private inline fun invokeListeners(fn: (callback: ActivityLifecycleCallbacks) -> Unit) {
        for (i in lifecycleCallbacks.size - 1 downTo 0) {
            val callback = lifecycleCallbacks[i]
            fn.invoke(callback)
        }
    }
}
