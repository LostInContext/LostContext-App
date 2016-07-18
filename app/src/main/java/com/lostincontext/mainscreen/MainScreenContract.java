package com.lostincontext.mainscreen;

import android.app.PendingIntent;

import com.lostincontext.commons.BasePresenter;
import com.lostincontext.commons.BaseView;
import com.lostincontext.commons.list.EmptyListCallback;
import com.lostincontext.data.rules.Rule;

import java.util.List;


public interface MainScreenContract {

    interface View extends BaseView<Presenter> {

        PendingIntent getPendingIntent();

        void openRuleCreationScreen();

        void setRules(List<Rule> rules);
    }

    interface Presenter extends BasePresenter, EmptyListCallback {

        void onFabClicked();
    }
}
