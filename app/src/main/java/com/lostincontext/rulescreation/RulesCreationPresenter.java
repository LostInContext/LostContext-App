package com.lostincontext.rulescreation;

import android.content.Intent;

import com.lostincontext.data.FenceCreator;
import com.lostincontext.data.location.repo.LocationRepository;
import com.lostincontext.rulescreation.display.EditLocationDialog;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;

public class RulesCreationPresenter implements RulesCreationContract.Presenter {

    private RulesCreationContract.View view;
    private LocationRepository locationRepository;


    @Override public void start() {

       // view.showDialog();
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EditLocationDialog.PLACE_PICKER_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                view.setPlace(data);
            }
        }
    }



    @Override public void onRuleCreationItemClick(FenceCreator fence) {

    }

    @Inject
    RulesCreationPresenter(RulesCreationContract.View view, LocationRepository locationRepository) {
        this.view = view;
        this.locationRepository = locationRepository;
    }

    @Inject void setup() {
        view.setPresenter(this);
    }

}
