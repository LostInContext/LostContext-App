package com.lostincontext.rulecreate

import android.os.Bundle
import com.lostincontext.data.playlist.Playlist
import com.lostincontext.data.rulesV2.Condition
import java.util.*
import javax.inject.Inject

class RuleCreatePresenter : RuleCreateContract.Presenter {


    var playlist: Playlist? = null

    private val items = ArrayList<Condition>()

    private val view: RuleCreateContract.View

    @Inject internal constructor(view: RuleCreateContract.View,
                                 icicle: Bundle?) {

        this.view = view
        icicle?.let { playlist = icicle.getParcelable(KEY_PLAYLIST) }
    }

    override fun start() {
        playlist?.let { view.setPlaylist(playlist) }
    }

    override fun saveState(outState: Bundle) {
        outState.putParcelable(KEY_PLAYLIST, playlist)
    }

    override fun onPlusButtonClick() = view.pickACondition(items.size + 1)

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


    companion object {
        const val KEY_PLAYLIST = "PlaylistRuleCreatePresenter"
    }
}