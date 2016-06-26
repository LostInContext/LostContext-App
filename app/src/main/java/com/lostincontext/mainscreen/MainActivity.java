package com.lostincontext.mainscreen;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.lostincontext.R;

import javax.inject.Inject;

import static com.lostincontext.utils.ActivityUtils.addFragmentToActivity;


public class MainActivity extends AppCompatActivity {


    @Inject
    MainScreenPresenter mainScreenPresenter;

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
        DaggerMainScreenFragmentComponent.builder()
                .mainScreenPresenterModule(new MainScreenPresenterModule(fragment))
                .build()
                .inject(this);

    }


}
