package com.lostincontext.ruledetails.items;

import com.lostincontext.ruledetails.pick.PickerDialogCallback;

public interface FenceItemCallback extends PickerDialogCallback {

    void onLinkClick(FenceItem item);

    void onPlaylistPickerClick();

}
