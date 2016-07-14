package com.lostincontext.rulescreation;

import android.content.Intent;

import com.google.android.gms.location.places.Place;
import com.lostincontext.commons.BasePresenter;
import com.lostincontext.commons.BaseView;

public class RulesCreationContract {

    interface View extends BaseView<RulesCreationContract.Presenter> {
        void showDialog();
        void setPlace(Intent place);

    }

    interface Presenter extends BasePresenter {

        @Override void start();
        void onActivityResult(int requestCode, int resultCode, Intent data);

    }
}
