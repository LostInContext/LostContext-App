package com.lostincontext.ruledetails;

import com.google.android.gms.maps.model.LatLng;
import com.lostincontext.commons.BasePresenter;
import com.lostincontext.commons.BaseView;
import com.lostincontext.commons.list.Section;
import com.lostincontext.data.playlist.Playlist;
import com.lostincontext.ruledetails.items.FenceItem;
import com.lostincontext.ruledetails.items.FenceItemCallback;
import com.lostincontext.rulescreation.display.RuleCreationItemCallback;

import java.util.List;


public class RuleDetailsContract {

    interface View extends BaseView<Presenter> {

        void setItems(List<FenceItem> items);

        void displayFenceChoice();

        void notifyItemInserted(int position);

        void pickAPlaylist();

        void notifyItemChanged(int position);

        void showPlaylist(Playlist playlist);

        void notifyItemRangeInserted(int positionStart, int itemCount);

        void showLocationPicker(String name);
    }


    interface Presenter extends BasePresenter, FenceItemCallback, RuleCreationItemCallback {

        List<Section> provideFenceChoices();

        void onPlaylistPicked(Playlist playlist);

        void onPlacePicked(String savedPlaceName, LatLng latLng);
    }
}
