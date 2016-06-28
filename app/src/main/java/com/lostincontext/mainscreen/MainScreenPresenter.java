package com.lostincontext.mainscreen;


import javax.inject.Inject;

public class MainScreenPresenter implements MainScreenContract.Presenter {

    private MainScreenContract.View view;

    @Override
    public void start() {

    }

    @Inject
    public MainScreenPresenter(MainScreenContract.View view) {
        this.view = view;
    }

    @Inject
    void setupListeners() {
        view.setPresenter(this);
    }

}


