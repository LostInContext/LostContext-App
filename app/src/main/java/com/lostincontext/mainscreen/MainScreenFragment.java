package com.lostincontext.mainscreen;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.awareness.fence.FenceState;
import com.lostincontext.R;

import static com.google.common.base.Preconditions.checkNotNull;


public class MainScreenFragment extends Fragment implements MainScreenContract.View {

    private static final String TAG = MainScreenFragment.class.getSimpleName();
    private static final String FENCE_RECEIVER_ACTION = "com.lostincontext.fenceAction";

    private MainScreenContract.Presenter presenter;

    MyFenceReceiver myFenceReceiver = new MyFenceReceiver();

    public static MainScreenFragment newInstance() {
        return new MainScreenFragment();
    }


    public MainScreenFragment() { }


    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    public PendingIntent getPendingIntent() {
        Intent intent = new Intent(FENCE_RECEIVER_ACTION);
        return PendingIntent.getBroadcast(this.getContext().getApplicationContext(), 0, intent, 0);
    }

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().registerReceiver(myFenceReceiver, new IntentFilter(FENCE_RECEIVER_ACTION));  // TODO POC
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mainscreen, container, false);

        return root;
    }

    @Override
    public void setPresenter(MainScreenContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }


    public class MyFenceReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            FenceState fenceState = FenceState.extract(intent);
            Log.i(TAG, "onReceive : fenceKey : " + fenceState.getFenceKey());

            if (TextUtils.equals(fenceState.getFenceKey(), "headphone")) {
                switch (fenceState.getCurrentState()) {
                    case FenceState.TRUE:
                        Log.i(TAG, "Headphones are plugged in.");
                        Toast.makeText(MainScreenFragment.this.getContext(), "Headphones are plugged in", Toast.LENGTH_SHORT).show();
                        break;
                    case FenceState.FALSE:
                        Log.i(TAG, "Headphones are NOT plugged in.");
                        Toast.makeText(MainScreenFragment.this.getContext(), "Headphones are NOT plugged in", Toast.LENGTH_SHORT).show();

                        break;
                    case FenceState.UNKNOWN:
                        Log.i(TAG, "The headphone fence is in an unknown state.");
                        break;
                }
            }
        }
    }
}