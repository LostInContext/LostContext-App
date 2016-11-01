package com.lostincontext.rulecreate

import android.os.Bundle
import com.lostincontext.data.playlist.Playlist
import com.lostincontext.data.rulesV2.Condition
import com.lostincontext.utils.logD
import java.util.*
import javax.inject.Inject

class RuleCreatePresenter @Inject constructor(private val view: RuleCreateContract.View,
                                              icicle: Bundle?) : RuleCreateContract.Presenter {


    var playlist: Playlist? = null

    private val items: ArrayList<Condition>

    init {
        if (icicle == null) items = ArrayList<Condition>()
        else {
            items = icicle.getParcelableArrayList(KEY_CONDITIONS)
            playlist = icicle.getParcelable(KEY_PLAYLIST)
        }
    }

    override fun start() {
        playlist?.let { view.setPlaylist(playlist) }
        view.setConditions(items)
        logD("fbl") { "items : $items" }

    }

    override fun saveState(outState: Bundle) {
        outState.putParcelable(KEY_PLAYLIST, playlist)
        outState.putParcelableArrayList(KEY_CONDITIONS, items)
    }

    override fun onPlusButtonClick() = view.pickACondition(items.size + 1)

    override fun onPlaylistPickClick() = view.pickAPlaylist()

    override fun onConditionAdded(condition: Condition) {
        items.add(condition)
    }

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
        items.remove(condition)
        view.remove(condition)
    }


    companion object {
        const val KEY_PLAYLIST = "PlaylistRuleCreatePresenter"
        const val KEY_CONDITIONS = "ConditionsRuleCreatePresenter"
    }


}