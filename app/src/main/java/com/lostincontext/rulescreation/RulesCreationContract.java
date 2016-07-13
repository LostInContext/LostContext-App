package com.lostincontext.rulescreation;

import com.lostincontext.commons.BasePresenter;
import com.lostincontext.commons.BaseView;

public class RulesCreationContract {

    interface View extends BaseView<RulesCreationContract.Presenter> {

    }

    interface Presenter extends BasePresenter {

        @Override void start();

    }
}
