package com.lostincontext.rulecreate

import android.os.Bundle
import com.lostincontext.data.playlist.Playlist
import com.lostincontext.data.rulesV2.Condition
import javax.inject.Inject

class RuleCreatePresenter : RuleCreateContract.Presenter {


    var playlist: Playlist? = null

    override fun saveState(outState: Bundle) {
    }


    private val view: RuleCreateContract.View

    @Inject internal constructor(view: RuleCreateContract.View,
                                 icicle: Bundle?) {

        this.view = view

        if (icicle != null) {
            // todo fetch saved state here
        }

    }

    override fun start() {
    }

    override fun onPlusButtonClick() = view.showRuleDetailsActivity()

    override fun onPlaylistPickClick() = view.pickAPlaylist()

    override fun onPlaylistPicked(playlist: Playlist) {
        this.playlist = playlist
        view.setPlaylist(playlist)
    }

    override fun onMenuItemClick(itemId: Int): Boolean {

        return false
    }

    override fun onConditionClick(condition: Condition) {

    }

    override fun onDeleteButtonClick(condition: Condition) {
    }


}