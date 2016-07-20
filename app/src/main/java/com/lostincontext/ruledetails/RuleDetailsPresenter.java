package com.lostincontext.ruledetails;

import com.lostincontext.data.FenceCreator;

import javax.inject.Inject;

public class RuleDetailsPresenter implements RuleDetailsContract.Presenter {


    private final RuleDetailsContract.View view;

    @Inject RuleDetailsPresenter(RuleDetailsContract.View view) {
        this.view = view;
    }

    @Inject void setup() {
        view.setPresenter(this);
    }


    @Override public void start() {

    }

    @Override public void onRuleCreationItemClick(FenceCreator fence) {

    }

    @Override public void onPlaylistPickerClick() {

    }
}
