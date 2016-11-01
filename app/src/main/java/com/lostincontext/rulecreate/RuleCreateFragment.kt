package com.lostincontext.rulecreate

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import com.genius.groupie.GroupAdapter
import com.genius.groupie.UpdatingGroup
import com.lostincontext.R
import com.lostincontext.application.LostApplication
import com.lostincontext.awareness.AwarenessModule
import com.lostincontext.commons.BaseActivity
import com.lostincontext.condition.CONDITION_REQUEST_CODE
import com.lostincontext.condition.ConditionActivity
import com.lostincontext.condition.EXTRA_CONDITION
import com.lostincontext.condition.PLAYLIST_PICKER_REQUEST_CODE
import com.lostincontext.data.playlist.Playlist
import com.lostincontext.data.rulesV2.Condition
import com.lostincontext.data.rulesV2.Rule
import com.lostincontext.databinding.RuleCreateScreenFragmentBinding
import com.lostincontext.playlists.PlaylistsContract
import com.lostincontext.rulecreate.RuleCreateContract.EXTRA_RULE
import com.lostincontext.users.UsersActivity
import com.lostincontext.utils.logD
import java.util.*
import javax.inject.Inject

class RuleCreateFragment : Fragment(), RuleCreateContract.View {

    @Inject lateinit internal var presenter: RuleCreatePresenter

    private lateinit var binding: RuleCreateScreenFragmentBinding

    private lateinit var playlistItem: PlaylistItem

    private lateinit var adapter: GroupAdapter

    private lateinit var scribe: Scribe

    private val items = ArrayList<ConditionItem>()
    private val group = UpdatingGroup()

    private var playlist: Playlist? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        DaggerRuleCreateComponent.builder()
                .ruleCreatePresenterModule(RuleCreatePresenterModule(this, savedInstanceState))
                .applicationComponent((activity.application as LostApplication).appComponent)
                .awarenessModule(AwarenessModule(activity as BaseActivity))
                .build()
                .inject(this)
    }


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<RuleCreateScreenFragmentBinding>(inflater,
                                                                           R.layout.rule_create_screen_fragment,
                                                                           container,
                                                                           false)
        binding.plusButton.callback = presenter


        val toolbar = binding.toolbar
        val appCompatActivity = activity as AppCompatActivity
        appCompatActivity.setSupportActionBar(toolbar)

        val recyclerView = binding.recyclerView

        adapter = GroupAdapter()

        playlistItem = PlaylistItem(context, presenter)
        adapter.add(playlistItem)
        adapter.add(group)

        recyclerView.adapter = adapter

        scribe = Scribe(context)
        val onScrollListener = OnScrollListener()

        onScrollListener.listener = binding.scrimLayout
        recyclerView.addOnScrollListener(onScrollListener)

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        binding.playlist = playlist

        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.start()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        presenter.saveState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.create_rule_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = presenter.onMenuItemClick(item.itemId)

    override fun setup(rule: Rule) {
        logD(TAG) {"setup : $rule"}
        val returnIntent = Intent()
        returnIntent.putExtra(EXTRA_RULE, rule)
        activity.setResult(Activity.RESULT_OK, returnIntent)
    }

    override fun setPlaylist(playlist: Playlist?) {
        playlistItem.playlist = playlist
        binding.playlist = playlist
    }

    override fun setConditions(conditions: List<Condition>) {
        conditions.forEachIndexed { i, condition ->
            val conditionItem = ConditionItem(presenter,
                                              items,
                                              condition,
                                              scribe)
            items.add(conditionItem)
        }
        group.update(items)
    }


    override fun pickACondition(index: Int) {
        val intent = Intent(this.context, ConditionActivity::class.java)
        intent.putExtra(RuleCreateContract.EXTRA_INDEX, index)
        startActivityForResult(intent, CONDITION_REQUEST_CODE)
    }

    override fun pickAPlaylist() {
        val intent = Intent(this.context, UsersActivity::class.java)
        startActivityForResult(intent, PLAYLIST_PICKER_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) return
        when {
            requestCode == PLAYLIST_PICKER_REQUEST_CODE && resultCode == RESULT_OK -> {
                val playlist = data.getParcelableExtra<Playlist>(PlaylistsContract.EXTRA_PLAYLIST)
                presenter.onPlaylistPicked(playlist)
            }
            requestCode == CONDITION_REQUEST_CODE && resultCode == RESULT_OK -> {
                val condition = data.getParcelableExtra<Condition>(EXTRA_CONDITION)
                logD(TAG) { "received condition : $condition" }
                items.add(ConditionItem(presenter, items, condition, scribe))
                group.update(items)
                presenter.onConditionAdded(condition)
            }
        }

    }

    override fun remove(condition: Condition) {
        val item = items.find { it.condition == condition }
        items.remove(item)
        group.update(items)
        items.forEachIndexed { i, item -> group.notifyItemChanged(i) }
    }

    companion object {
        val TAG: String = RuleCreateFragment::class.java.name
        fun newInstance(): RuleCreateFragment = RuleCreateFragment()
    }

    inner class OnScrollListener : RecyclerView.OnScrollListener() {

        var listener: HeaderScrollListener? = null

        var headerScroll = 0

        override fun onScrolled(recyclerView: RecyclerView,
                                dx: Int,
                                dy: Int) {

            val count = recyclerView.childCount
            var childFound: View? = null

            for (i in 0..count - 1) {
                val child = recyclerView.getChildAt(i) ?: continue
                val holder = recyclerView.getChildViewHolder(child) ?: continue
                if (holder.itemViewType == R.layout.item_playlist_edit_screen) {
                    childFound = child
                    break
                }
            }

            if (childFound != null) {
                headerScroll = -childFound.top
            } else {
                // since we don't get all the pixel updates, a very fast swipe down the list can make us miss a step or two.
                // To avoid obtaining an header with an incorrect offset by a couple of pixels, if not present, we sent a max scroll value instead.
                // Since onScrolled caps its behavior, it works out elegantly.
                headerScroll = Integer.MAX_VALUE
            }
            listener?.onScrolled(headerScroll)

        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        }
    }

}