package com.lostincontext.mainscreen;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.lostincontext.R;
import com.lostincontext.googleapi.Awareness;
import com.lostincontext.googleapi.DaggerAwarenessComponent;
import com.lostincontext.googleapi.AwarenessComponent;
import com.lostincontext.googleapi.AwarenessModule;

import javax.inject.Inject;

import static com.lostincontext.utils.ActivityUtils.addFragmentToActivity;


public class MainActivity extends AppCompatActivity {

    @Inject MainScreenPresenter mainScreenPresenter;

    @Inject Awareness awareness;

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
        AwarenessComponent googleApiComponent = DaggerAwarenessComponent.builder()
                .awarenessModule(new AwarenessModule(this)).build();

        // Create the presenter
        DaggerMainScreenFragmentComponent.builder()
                .mainScreenPresenterModule(new MainScreenPresenterModule(fragment))
                .awarenessComponent(googleApiComponent)
                .build()
                .inject(this);
    }


}
