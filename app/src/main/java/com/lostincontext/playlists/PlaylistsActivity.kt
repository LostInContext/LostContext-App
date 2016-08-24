package com.lostincontext.playlists

import android.os.Bundle
import com.lostincontext.R
import com.lostincontext.application.LostApplication
import com.lostincontext.commons.BaseActivity
import com.lostincontext.utils.addFragmentToActivity
import javax.inject.Inject


class PlaylistsActivity : BaseActivity() {


    @Inject lateinit internal var presenter: PlaylistsPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.playlists_screen)

        val fm = supportFragmentManager
        var fragment: PlaylistsFragment? = fm.findFragmentById(R.id.contentFrame) as PlaylistsFragment?

        if (fragment == null) {
            fragment = PlaylistsFragment.newInstance()
            addFragmentToActivity(fm, fragment, R.id.contentFrame)
        }

        DaggerPlaylistsComponent.builder()
                .playlistsPresenterModule(PlaylistsPresenterModule(fragment))
                .applicationComponent((application as LostApplication).appComponent)
                .build()
                .inject(this)
    }
}
