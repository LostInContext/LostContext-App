package com.lostincontext.users;


import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lostincontext.R
import com.lostincontext.application.LostApplication
import com.lostincontext.commons.list.StatefulAdapter
import com.lostincontext.condition.PLAYLIST_PICKER_REQUEST_CODE
import com.lostincontext.data.playlist.Playlist
import com.lostincontext.data.user.User
import com.lostincontext.databinding.UsersScreenFragmentBinding
import com.lostincontext.playlists.PlaylistsActivity
import com.lostincontext.playlists.PlaylistsContract
import javax.inject.Inject

class UsersFragment : Fragment(), UsersContract.View {

    @Inject lateinit internal var presenter: UsersPresenter

    private lateinit var adapter: UsersAdapter
    private lateinit var binding: UsersScreenFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerUsersComponent.builder()
                .usersPresenterModule(UsersPresenterModule(this, savedInstanceState))
                .applicationComponent((activity.application as LostApplication).appComponent)
                .build()
                .inject(this)
    }


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate<UsersScreenFragmentBinding>(inflater,
                                                                      R.layout.users_screen_fragment,
                                                                      container,
                                                                      false)


        val recyclerView = binding.recyclerView

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        adapter = UsersAdapter(presenter, presenter)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)

        binding.presenter = presenter

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

    //region view implementation

    override fun setUsers(users: List<User>) = adapter.setUsers(users)

    override fun setContentState(state: StatefulAdapter.ContentState) {
        adapter.currentState = state
    }

    override fun openPlaylistsScreen(user: User) {
        val intent = Intent(this.context, PlaylistsActivity::class.java)
        intent.putExtra(PlaylistsContract.EXTRA_USER_ID, user.id)
        startActivityForResult(intent, PLAYLIST_PICKER_REQUEST_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null && requestCode == PLAYLIST_PICKER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val playlist = data.getParcelableExtra<Playlist>(PlaylistsContract.EXTRA_PLAYLIST)
            val returnIntent = Intent()
            returnIntent.putExtra(PlaylistsContract.EXTRA_PLAYLIST, playlist)
            val activity = activity
            activity.setResult(Activity.RESULT_OK, returnIntent)
            activity.finish()
        }
    }


    //endregion

    companion object {
        fun newInstance(): UsersFragment = UsersFragment()
    }
}
