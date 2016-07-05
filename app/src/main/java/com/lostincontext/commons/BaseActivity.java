package com.lostincontext.commons;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.CallSuper;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseActivity extends AppCompatActivity {

    private final List<ActivityLifecycleCallbacks> lifecycleListeners = new ArrayList<>();


    public interface ActivityLifecycleCallbacks {

        void onActivityCreated(BaseActivity activity);

        void onActivityStarted(BaseActivity activity);

        void onActivityResumed(BaseActivity activity);

        void onActivityPaused(BaseActivity activity);

        void onActivityStopped(BaseActivity activity);

        void onActivityDestroyed(BaseActivity activity);

    }

    @MainThread
    public void registerLifecycleCallbacks(ActivityLifecycleCallbacks listener) {
        lifecycleListeners.add(listener);
    }

    @MainThread
    public void unregisterLifecycleCallbacks(ActivityLifecycleCallbacks listener) {
        lifecycleListeners.remove(listener);
    }

    @Override
    @CallSuper
    public void onCreate(Bundle savedInstanceState,
                         PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        for (int i = lifecycleListeners.size() - 1; i >= 0; i--) {
            ActivityLifecycleCallbacks listener = lifecycleListeners.get(i);
            listener.onActivityCreated(this);
        }
    }

    @Override @CallSuper protected void onStart() {
        super.onStart();
        for (int i = lifecycleListeners.size() - 1; i >= 0; i--) {
            ActivityLifecycleCallbacks listener = lifecycleListeners.get(i);
            listener.onActivityStarted(this);
        }
    }

    @Override @CallSuper protected void onResume() {
        super.onResume();

        for (int i = lifecycleListeners.size() - 1; i >= 0; i--) {
            ActivityLifecycleCallbacks listener = lifecycleListeners.get(i);
            listener.onActivityResumed(this);
        }
    }

    @Override @CallSuper protected void onPause() {

        for (int i = lifecycleListeners.size() - 1; i >= 0; i--) {
            ActivityLifecycleCallbacks listener = lifecycleListeners.get(i);
            listener.onActivityPaused(this);
        }

        super.onPause();
    }

    @Override @CallSuper protected void onStop() {

        for (int i = lifecycleListeners.size() - 1; i >= 0; i--) {
            ActivityLifecycleCallbacks listener = lifecycleListeners.get(i);
            listener.onActivityStopped(this);
        }

        super.onStop();
    }

    @Override @CallSuper protected void onDestroy() {

        for (int i = lifecycleListeners.size() - 1; i >= 0; i--) {
            ActivityLifecycleCallbacks listener = lifecycleListeners.get(i);
            listener.onActivityDestroyed(this);
        }

        super.onDestroy();
    }

}
