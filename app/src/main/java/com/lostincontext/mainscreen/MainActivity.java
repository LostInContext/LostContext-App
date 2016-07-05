package com.lostincontext.mainscreen;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.lostincontext.R;
import com.lostincontext.awareness.AwarenessComponent;
import com.lostincontext.awareness.AwarenessModule;
import com.lostincontext.awareness.DaggerAwarenessComponent;
import com.lostincontext.commons.BaseActivity;

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

        // Create the Google API client :
        AwarenessComponent awarenessComponent = DaggerAwarenessComponent.builder()
                .awarenessModule(new AwarenessModule(this)).build();

        // Create the presenter
        DaggerMainScreenComponent.builder()
                .mainScreenPresenterModule(new MainScreenPresenterModule(fragment))
                .awarenessComponent(awarenessComponent)
                .build()
                .inject(this);
    }


}
