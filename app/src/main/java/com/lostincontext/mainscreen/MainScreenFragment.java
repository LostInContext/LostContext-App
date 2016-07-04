package com.lostincontext.mainscreen;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lostincontext.R;
import com.lostincontext.that.ThatService;

import static com.google.common.base.Preconditions.checkNotNull;


public class MainScreenFragment extends Fragment implements MainScreenContract.View {

    private MainScreenContract.Presenter presenter;


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
        Intent intent = new Intent(this.getContext(), ThatService.class);
        return PendingIntent.getService(this.getContext().getApplicationContext(),
                                        0,
                                        intent,
                                        0);
    }

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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


}