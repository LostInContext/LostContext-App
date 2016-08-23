package com.lostincontext.ruledetails;

import android.app.PendingIntent;

import com.google.android.gms.maps.model.LatLng;
import com.lostincontext.commons.BasePresenter;
import com.lostincontext.commons.BaseView;
import com.lostincontext.commons.list.Section;
import com.lostincontext.ruledetails.pick.GridBottomSheetItem;
import com.lostincontext.data.playlist.Playlist;
import com.lostincontext.ruledetails.items.FenceItem;
import com.lostincontext.ruledetails.items.FenceItemCallback;
import com.lostincontext.ruledetails.pick.PickerDialogCallback;
import com.lostincontext.ruledetails.pick.PlusButtonCallback;

import java.util.EnumSet;
import java.util.List;


public class RuleDetailsContract {

    public enum RuleErrors {
        NO_TITLE,
        NO_CONDITION,
        NO_PLAYLIST,
        SAVE_ERROR,
    }

    public static final Object LINK_CHANGED = new Object();


    interface View extends BaseView<Presenter> {

        void setItems(List<FenceItem> items);

        void displayFenceChoice();

        void notifyItemInserted(int position);

        void pickAPlaylist();

        void notifyItemChanged(int position, Object payload);

        void showPlaylist(Playlist playlist);

        void checkPermissionsAndShowLocationPicker(String name, GridBottomSheetItem item);

        void setRuleName(String ruleName);

        PendingIntent getPendingIntentFor(Playlist playlist);

        void finishActivity();

        void showSnack(EnumSet<RuleErrors> errors);
    }


    interface Presenter extends BasePresenter, FenceItemCallback, PlusButtonCallback, PickerDialogCallback {

        List<Section> provideFenceChoices();

        void onPlaylistPicked(Playlist playlist);

        void onPlacePicked(String savedPlaceName, GridBottomSheetItem item, LatLng latLng);

        boolean onMenuItemClick(int itemId);
    }
}
