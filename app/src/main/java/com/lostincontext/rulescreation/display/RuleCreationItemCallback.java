package com.lostincontext.rulescreation.display;

import com.lostincontext.data.FenceCreator;
import com.lostincontext.ruledetails.pick.PickerDialogCallback;

public interface RuleCreationItemCallback extends PickerDialogCallback {

    void onRuleCreationItemClick(FenceCreator fence);

    void onPlaylistPickerClick();

    void onPlusButtonClick();

}
