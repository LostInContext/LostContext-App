package com.lostincontext.ruledetails;

import com.lostincontext.commons.BasePresenter;
import com.lostincontext.commons.BaseView;
import com.lostincontext.commons.list.Section;
import com.lostincontext.rulescreation.display.RuleCreationItemCallback;

import java.util.List;


public class RuleDetailsContract {

    interface View extends BaseView<Presenter> {

        void setSections(List<Section> sections);

    }


    interface Presenter extends BasePresenter, RuleCreationItemCallback {

    }
}
