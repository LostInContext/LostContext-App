package com.lostincontext.mainscreen;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lostincontext.R;
import com.lostincontext.commons.list.SpacesItemDecoration;
import com.lostincontext.data.rules.Rule;
import com.lostincontext.databinding.MainScreenFragmentBinding;
import com.lostincontext.ruledetails.RuleDetailsActivity;
import com.lostincontext.rulescreation.RulesCreationActivity;
import com.lostincontext.that.ThatService;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


public class MainScreenFragment extends Fragment implements MainScreenContract.View {

    private MainScreenContract.Presenter presenter;
    private MainScreenAdapter adapter;


    public static MainScreenFragment newInstance() {
        return new MainScreenFragment();
    }


    public MainScreenFragment() { }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        MainScreenFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.main_screen_fragment, container, false);
        binding.setPresenter(presenter);


        RecyclerView recyclerView = binding.recyclerView;
        Resources resources = getResources();
        final int span = resources.getInteger(R.integer.list_span);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), span);
        recyclerView.setLayoutManager(layoutManager);
        int space = resources.getDimensionPixelSize(R.dimen.grid_spacing);
        recyclerView.addItemDecoration(new SpacesItemDecoration(space, span));

        adapter = new MainScreenAdapter(presenter);

        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override public int getSpanSize(int position) {
                switch (adapter.getCurrentState()) {
                    case LOADING:
                    case ERROR:
                    case EMPTY:
                        return span;

                    case CONTENT:
                        return 1;

                    default:
                        throw new RuntimeException("invalid state");
                }
            }
        });
        recyclerView.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override public void setPresenter(MainScreenContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }

    public PendingIntent getPendingIntent() {
        Intent intent = new Intent(this.getContext(), ThatService.class);
        return PendingIntent.getService(this.getContext().getApplicationContext(),
                                        0,
                                        intent,
                                        0);
    }


    @Override public void openRuleCreationScreen() {
        Intent intent = new Intent(this.getContext(), RuleDetailsActivity.class);
        startActivity(intent);
    }

    @Override public void setRules(List<Rule> rules) {
        adapter.setRules(rules);
    }

}