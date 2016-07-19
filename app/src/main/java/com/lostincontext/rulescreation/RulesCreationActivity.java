package com.lostincontext.rulescreation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.lostincontext.R;
import com.lostincontext.commons.BaseActivity;
import com.lostincontext.data.location.repo.LocationRepositoryModule;

import javax.inject.Inject;

import static com.lostincontext.utils.Activities.addFragmentToActivity;

public class RulesCreationActivity extends BaseActivity {

    @Inject RulesCreationPresenter presenter;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rules_creation_screen);

        FragmentManager fm = getSupportFragmentManager();
        RulesCreationFragment fragment = (RulesCreationFragment) fm.findFragmentById(R.id.contentFrame);
        if (fragment == null) {
            fragment = RulesCreationFragment.newInstance();
            addFragmentToActivity(fm, fragment, R.id.contentFrame);
        }

        DaggerRulesCreationComponent.builder()
                .rulesCreationPresenterModule(new RulesCreationPresenterModule(fragment))
                .locationRepositoryModule(new LocationRepositoryModule(this))
                .build()
                .inject(this);

    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onActivityResult(requestCode, resultCode, data);
    }
}
