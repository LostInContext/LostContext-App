package com.lostincontext.rulescreation;

import android.content.Intent;

import com.lostincontext.rulescreation.display.EditLocationDialog;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;

public class RulesCreationPresenter implements RulesCreationContract.Presenter {

    private RulesCreationContract.View view;


    @Override public void start() {

        view.showDialog();
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EditLocationDialog.PLACE_PICKER_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                view.setPlace(data);
            }
        }
    }

    @Inject RulesCreationPresenter(RulesCreationContract.View view) {
        this.view = view;
    }

    @Inject void setup() {
        view.setPresenter(this);
    }

}
