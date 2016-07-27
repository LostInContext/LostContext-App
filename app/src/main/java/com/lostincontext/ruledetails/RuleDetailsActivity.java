package com.lostincontext.ruledetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.lostincontext.R;
import com.lostincontext.commons.BaseActivity;
import com.lostincontext.data.location.repo.LocationRepositoryModule;

import javax.inject.Inject;

import static com.lostincontext.utils.Activities.addFragmentToActivity;


public class RuleDetailsActivity extends BaseActivity {


    @Inject RuleDetailsPresenter presenter;


    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rules_creation_screen);

        FragmentManager fm = getSupportFragmentManager();
        RuleDetailsFragment fragment = (RuleDetailsFragment) fm.findFragmentById(R.id.contentFrame);
        if (fragment == null) {
            fragment = RuleDetailsFragment.newInstance();
            addFragmentToActivity(fm, fragment, R.id.contentFrame);
        }

        DaggerRuleDetailsComponent.builder()
                .ruleDetailsPresenterModule(new RuleDetailsPresenterModule(fragment))
                .locationRepositoryModule(new LocationRepositoryModule(this))
                .build()
                .inject(this);

    }

}