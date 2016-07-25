package com.lostincontext.ruledetails;

import com.lostincontext.commons.BasePresenter;
import com.lostincontext.commons.BaseView;
import com.lostincontext.commons.list.Section;
import com.lostincontext.data.playlist.Playlist;
import com.lostincontext.rulescreation.display.RuleCreationItemCallback;

import java.util.List;


public class RuleDetailsContract {

    interface View extends BaseView<Presenter> {

        void setItems(List<RuleDetailsItem> items);

        void displayFenceChoice();

        void notifyItemAdded(int position);

        void pickAPlaylist();
    }


    interface Presenter extends BasePresenter, RuleCreationItemCallback {

        List<Section>  provideFenceChoices();

        void onPlaylistPicked(Playlist playlist);
    }
}
