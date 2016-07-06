package com.lostincontext.mainscreen;

import android.app.PendingIntent;
import android.content.Context;

import com.lostincontext.commons.BasePresenter;
import com.lostincontext.commons.BaseView;
import com.lostincontext.config.RuleConfiguration;


public interface MainScreenContract {

    interface View extends BaseView<Presenter> {

        PendingIntent getPendingIntent();
        RuleConfiguration getRuleConfiguration();

    }

    interface Presenter extends BasePresenter {

    }
}

