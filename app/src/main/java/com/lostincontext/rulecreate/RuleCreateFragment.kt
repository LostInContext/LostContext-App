package com.lostincontext.rulecreate

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.genius.groupie.GroupAdapter
import com.lostincontext.R
import com.lostincontext.application.LostApplication
import com.lostincontext.awareness.AwarenessModule
import com.lostincontext.commons.BaseActivity
import com.lostincontext.data.playlist.Playlist
import com.lostincontext.databinding.RuleCreateScreenFragmentBinding
import com.lostincontext.playlists.PlaylistsContract
import com.lostincontext.ruledetails.RuleDetailsActivity
import com.lostincontext.ruledetails.RuleDetailsFragment
import com.lostincontext.users.UsersActivity
import javax.inject.Inject

class RuleCreateFragment : Fragment(), View.OnClickListener, RuleCreateContract.View {

    @Inject lateinit internal var presenter: RuleCreatePresenter

    private lateinit var binding: RuleCreateScreenFragmentBinding

    private lateinit var playlistItem: PlaylistItem


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
        toolbar.title = "test"
        val appCompatActivity = activity as AppCompatActivity
        appCompatActivity.setSupportActionBar(toolbar)

        val recyclerView = binding.recyclerView

        val adapter = GroupAdapter(this)


        playlistItem = PlaylistItem(presenter)
        adapter.add(playlistItem)

        recyclerView.adapter = adapter

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager


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
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.create_rule_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = presenter.onMenuItemClick(item.itemId)


    override fun showRuleDetailsActivity() {
        val intent = Intent(this.context, RuleDetailsActivity::class.java)
        startActivity(intent)
    }

    override fun pickAPlaylist() {
        val intent = Intent(this.context, UsersActivity::class.java)
        startActivityForResult(intent, PLAYLIST_PICKER_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RuleDetailsFragment.PLAYLIST_PICKER_REQUEST_CODE
                && resultCode == Activity.RESULT_OK) {
            val playlist = data.getParcelableExtra<Playlist>(PlaylistsContract.EXTRA_PLAYLIST)
            presenter.onPlaylistPicked(playlist)
        }
    }

    override fun setPlaylist(playlist: Playlist) {
        playlistItem.playlist = playlist
    }


    companion object {
        fun newInstance(): RuleCreateFragment = RuleCreateFragment()
        val PLAYLIST_PICKER_REQUEST_CODE = 9001
    }

    override fun onClick(v: View) {
    }

}