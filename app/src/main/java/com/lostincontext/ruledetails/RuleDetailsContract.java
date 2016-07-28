package com.lostincontext.ruledetails;

import android.app.PendingIntent;

import com.google.android.gms.maps.model.LatLng;
import com.lostincontext.commons.BasePresenter;
import com.lostincontext.commons.BaseView;
import com.lostincontext.commons.list.Section;
import com.lostincontext.data.GridBottomSheetItem;
import com.lostincontext.data.playlist.Playlist;
import com.lostincontext.data.rules.Rule;
import com.lostincontext.ruledetails.items.FenceItem;
import com.lostincontext.ruledetails.items.FenceItemCallback;
import com.lostincontext.ruledetails.pick.PickerDialogCallback;
import com.lostincontext.ruledetails.pick.PlusButtonCallback;

import java.util.List;


public class RuleDetailsContract {

    interface View extends BaseView<Presenter> {

        void setItems(List<FenceItem> items);

        void displayFenceChoice();

        void notifyItemInserted(int position);

        void pickAPlaylist();

        void notifyItemChanged(int position);

        void showPlaylist(Playlist playlist);

        void showLocationPicker(String name, GridBottomSheetItem item);

        void setRule(Rule rule);

        PendingIntent getPendingIntent(Playlist playlist);

        void finishActivity();
    }


    interface Presenter extends BasePresenter, FenceItemCallback, PlusButtonCallback, PickerDialogCallback {

        List<Section> provideFenceChoices();

        void onPlaylistPicked(Playlist playlist);

        void onPlacePicked(String savedPlaceName, GridBottomSheetItem item, LatLng latLng);

        boolean onMenuItemClick(int itemId);
    }
}
