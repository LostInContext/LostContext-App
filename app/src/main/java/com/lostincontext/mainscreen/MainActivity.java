package com.lostincontext.mainscreen;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.lostincontext.R;
import com.lostincontext.awareness.AwarenessModule;
import com.lostincontext.commons.BaseActivity;
import com.lostincontext.data.rules.repo.RulesRepositoryModule;

import javax.inject.Inject;

import static com.lostincontext.utils.Activities.addFragmentToActivity;


public class MainActivity extends BaseActivity {

    @Inject MainScreenPresenter mainScreenPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);


        FragmentManager fm = getSupportFragmentManager();
        MainScreenFragment fragment = (MainScreenFragment) fm.findFragmentById(R.id.contentFrame);

        if (fragment == null) {
            fragment = MainScreenFragment.newInstance();
            addFragmentToActivity(fm, fragment, R.id.contentFrame);
        }

        // Create the presenter
        DaggerMainScreenComponent.builder()
                .mainScreenPresenterModule(new MainScreenPresenterModule(fragment))
                .awarenessModule(new AwarenessModule(this))
                .rulesRepositoryModule(new RulesRepositoryModule(this))
                .build()
                .inject(this);
    }


}
