package com.lostincontext.ruledetails;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lostincontext.R;
import com.lostincontext.commons.list.Section;
import com.lostincontext.databinding.RuleDetailsScreenFragmentBinding;

import java.util.List;


public class RuleDetailsFragment extends Fragment implements RuleDetailsContract.View {

    private RuleDetailsContract.Presenter presenter;


    public static RuleDetailsFragment newInstance() {
        return new RuleDetailsFragment();
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        RuleDetailsScreenFragmentBinding binding = DataBindingUtil.inflate(inflater,
                                                                           R.layout.rule_details_screen_fragment,
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
    public void setPresenter(RuleDetailsContract.Presenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public void setSections(List<Section> sections) {

    }


}
