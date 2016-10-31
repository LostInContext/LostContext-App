package com.lostincontext.rulecreate

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.*
import com.genius.groupie.GroupAdapter
import com.genius.groupie.UpdatingGroup
import com.lostincontext.R
import com.lostincontext.application.LostApplication
import com.lostincontext.awareness.AwarenessModule
import com.lostincontext.commons.BaseActivity
import com.lostincontext.condition.ConditionActivity
import com.lostincontext.condition.EXTRA_CONDITION
import com.lostincontext.data.playlist.Playlist
import com.lostincontext.data.rules.FenceNamer
import com.lostincontext.data.rulesV2.AtomicCondition
import com.lostincontext.data.rulesV2.Condition
import com.lostincontext.databinding.RuleCreateScreenFragmentBinding
import com.lostincontext.playlists.PlaylistsContract
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


    class Scribe(val context: Context) {

        val resources: Resources = context.resources
        val theme: Resources.Theme = context.theme

        fun describeCondition(condition: Condition): CharSequence {
            val description = resources.getString(R.string.when_x)
            return TextUtils.expandTemplate(description,
                                            describeAtomicConditions(condition.atomics))

        }

        fun describeAtomicConditions(conditions: List<AtomicCondition>): CharSequence {
            val namer = FenceNamer(context)

            val template = resources.getString(R.string.x_and_y)

            when (conditions.size) {
                0 -> return ""
                1 -> return conditions[0].fence.name(namer)
                else ->
                    return conditions.fold("" as CharSequence) { s, atomic ->
                        if (s.isEmpty()) atomic.fence.name(namer)
                        else TextUtils.expandTemplate(template,
                                                      s,
                                                      atomic.fence.name(namer))
                    }
            }


        }

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
                items.add(ConditionItem(presenter, items.size + 1, condition, scribe))
                group.update(items)
            }
        }

    }

    override fun setPlaylist(playlist: Playlist?) {
        playlistItem.playlist = playlist
        binding.playlist = playlist
    }


    companion object {
        val TAG: String = RuleCreateFragment::class.java.name
        fun newInstance(): RuleCreateFragment = RuleCreateFragment()
        const val PLAYLIST_PICKER_REQUEST_CODE = 9001
        const val CONDITION_REQUEST_CODE = 9002
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