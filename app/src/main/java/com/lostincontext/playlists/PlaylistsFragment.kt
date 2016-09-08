package com.lostincontext.playlists

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lostincontext.PlaylistLauncher
import com.lostincontext.R
import com.lostincontext.application.LostApplication
import com.lostincontext.commons.list.SpacesItemDecoration
import com.lostincontext.commons.list.StatefulAdapter
import com.lostincontext.data.playlist.Playlist
import com.lostincontext.databinding.PlaylistsScreenFragmentBinding
import javax.inject.Inject


class PlaylistsFragment : Fragment(), PlaylistsContract.View {

    @Inject lateinit internal var presenter: PlaylistsPresenter

    private lateinit var adapter: PlaylistsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerPlaylistsComponent.builder()
                .playlistsPresenterModule(PlaylistsPresenterModule(this))
                .applicationComponent((activity.application as LostApplication).appComponent)
                .build()
                .inject(this)
    }


    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<PlaylistsScreenFragmentBinding>(inflater!!,
                                                                              R.layout.playlists_screen_fragment,
                                                                              container,
                                                                              false)
        val recyclerView = binding.recyclerView
        val resources = resources
        val span = resources.getInteger(R.integer.grid_span)
        val layoutManager = GridLayoutManager(context, span)
        recyclerView.layoutManager = layoutManager
        val space = resources.getDimensionPixelSize(R.dimen.grid_spacing)
        recyclerView.addItemDecoration(SpacesItemDecoration(space, span))

        adapter = PlaylistsAdapter(presenter, presenter)

        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                when (adapter.currentState) {
                    StatefulAdapter.ContentState.LOADING,
                    StatefulAdapter.ContentState.ERROR,
                    StatefulAdapter.ContentState.EMPTY -> return span

                    StatefulAdapter.ContentState.CONTENT -> return 1

                    else -> throw RuntimeException("invalid state")
                }
            }
        }
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun setPlaylists(playlists: List<Playlist>) {
        adapter.setPlaylists(playlists)
    }

    override fun openDeezerFor(playlist: Playlist) {
        val launcher = PlaylistLauncher()
        launcher.launchPlaylist(context, playlist, false)
    }

    override fun returnResult(playlist: Playlist) {
        val returnIntent = Intent()
        returnIntent.putExtra(PlaylistsContract.EXTRA_PLAYLIST, playlist)
        val activity = activity
        activity.setResult(Activity.RESULT_OK, returnIntent)
        activity.finish()
    }

    companion object {

        fun newInstance(): PlaylistsFragment {
            return PlaylistsFragment()
        }
    }
}
