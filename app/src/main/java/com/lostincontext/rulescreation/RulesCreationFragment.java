package com.lostincontext.rulescreation;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lostincontext.R;
import com.lostincontext.databinding.RulesCreationScreenFragmentBinding;

public class RulesCreationFragment extends Fragment implements RulesCreationContract.View {

    private RulesCreationContract.Presenter presenter;

    public static RulesCreationFragment newInstance() {
        return new RulesCreationFragment();
    }


    public RulesCreationFragment() { }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RulesCreationScreenFragmentBinding binding = DataBindingUtil.inflate(inflater,
                                                                             R.layout.rules_creation_screen_fragment,
                                                                             container,
                                                                             false);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void setPresenter(RulesCreationContract.Presenter presenter) {
        this.presenter = presenter;
    }

}
