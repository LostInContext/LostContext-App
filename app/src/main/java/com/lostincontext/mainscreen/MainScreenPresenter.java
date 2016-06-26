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

    /**
     * Method injection is used here to safely reference {@code this} after the object is created.
     * For more information, see Java Concurrency in Practice.
     */
    @Inject
    void setupListeners() {
        view.setPresenter(this);
    }
}


