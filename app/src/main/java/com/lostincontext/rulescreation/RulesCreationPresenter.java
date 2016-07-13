package com.lostincontext.rulescreation;

import javax.inject.Inject;

public class RulesCreationPresenter implements RulesCreationContract.Presenter {

    private RulesCreationContract.View view;


    @Override public void start() {

    }

    @Inject RulesCreationPresenter(RulesCreationContract.View view) {
        this.view = view;
    }

    @Inject void setup() {
        view.setPresenter(this);
    }

}
