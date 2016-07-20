package com.lostincontext.ruledetails;

import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lostincontext.R;
import com.lostincontext.commons.list.Section;
import com.lostincontext.databinding.RuleDetailsScreenFragmentBinding;
import com.lostincontext.rulescreation.RulesCreationAdapter;

import java.util.List;


public class RuleDetailsFragment extends Fragment implements RuleDetailsContract.View {

    private RuleDetailsContract.Presenter presenter;

    private RuleDetailsAdapter adapter;


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

        RecyclerView recyclerView = binding.recyclerView;

        Resources resources = getResources();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RuleDetailsAdapter(presenter);




        recyclerView.setAdapter(adapter);


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
