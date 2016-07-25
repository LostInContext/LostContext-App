package com.lostincontext.rulescreation.display;

import com.lostincontext.data.FenceCreator;
import com.lostincontext.data.GridBottomSheetItem;

public interface RuleCreationItemCallback {

    void onRuleCreationItemClick(FenceCreator fence);

    void onPlaylistPickerClick();

    void onPlusButtonClick();

    void onGridBottomSheetItemClick(GridBottomSheetItem item);
}
