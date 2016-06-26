package com.lostincontext.mainscreen;

import com.lostincontext.commons.BasePresenter;
import com.lostincontext.commons.BaseView;


public interface MainScreenContract {

    interface View extends BaseView<Presenter> {
    }

    interface Presenter extends BasePresenter {

    }
}

