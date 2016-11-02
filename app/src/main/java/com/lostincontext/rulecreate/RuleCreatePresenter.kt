package com.lostincontext.rulecreate

import android.os.Bundle
import com.lostincontext.data.playlist.Playlist
import com.lostincontext.data.rule.Condition
import com.lostincontext.data.rule.Rule
import com.lostincontext.utils.logD
import java.util.*
import javax.inject.Inject

class RuleCreatePresenter @Inject constructor(private val view: RuleCreateContract.View,
                                              icicle: Bundle?) : RuleCreateContract.Presenter {


    var playlist: Playlist? = null

    private val items: ArrayList<Condition>

    private val id: String

    init {
        if (icicle == null) {
            items = ArrayList<Condition>()
            id = UUID.randomUUID().toString()
        } else {
            items = icicle.getParcelableArrayList(KEY_CONDITIONS)
            playlist = icicle.getParcelable(KEY_PLAYLIST)
            id = icicle.getString(KEY_UNIQUE_ID)
        }
    }

    override fun start() {
        playlist?.let {
            view.setPlaylist(playlist)
            view.setup(Rule(items, playlist!!, id))
        }
        view.setConditions(items)
        logD(TAG) { "items : $items" }
    }

    override fun saveState(outState: Bundle) {
        outState.putParcelable(KEY_PLAYLIST, playlist)
        outState.putParcelableArrayList(KEY_CONDITIONS, items)
        outState.putString(KEY_UNIQUE_ID, id)
    }

    override fun onPlusButtonClick() = view.pickACondition(items.size + 1)

    override fun onPlaylistPickClick() = view.pickAPlaylist()

    override fun onConditionAdded(condition: Condition) {
        logD(TAG) { "onConditionAdded : $condition" }
        if (condition.atomics.isEmpty()) return // should we maybe display something ?

        items.add(condition)
        playlist?.let { view.setup(Rule(items, playlist!!, id)) }
        view.setConditions(items)
    }

    override fun onPlaylistPicked(playlist: Playlist) {
        this.playlist = playlist
        view.setPlaylist(playlist)
        view.setup(Rule(items, playlist, id))
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
        const val KEY_PLAYLIST = "Playlist-RuleCreatePresenter"
        const val KEY_CONDITIONS = "Conditions-RuleCreatePresenter"
        const val KEY_UNIQUE_ID = "id-RuleCreatePresenter"
        private val TAG: String = RuleCreatePresenter::class.java.simpleName
    }


}