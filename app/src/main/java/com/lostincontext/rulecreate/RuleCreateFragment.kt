package com.lostincontext.rulecreate

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
import com.lostincontext.commons.images.DeezerImageUrlGenerator
import com.lostincontext.data.playlist.Creator
import com.lostincontext.data.playlist.Playlist
import com.lostincontext.databinding.RuleCreateScreenFragmentBinding
import com.lostincontext.ruledetails.RuleDetailsActivity
import javax.inject.Inject

class RuleCreateFragment : Fragment(), View.OnClickListener, RuleCreateContract.View {

    @Inject lateinit internal var presenter: RuleCreatePresenter
    private lateinit var binding: RuleCreateScreenFragmentBinding

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
        recyclerView.adapter = adapter

        val playlistItem = PlaylistItem(Playlist(1687197983L,
                                                 "test",
                                                 Creator(1687197983L, "tes"),
                                                 "07f77e2b833a9e31a852ca89cab041bc",
                                                 DeezerImageUrlGenerator.TYPE_COVER))
        adapter.add(playlistItem)
        adapter.add(playlistItem)
        adapter.add(playlistItem)
        adapter.add(playlistItem)
        adapter.add(playlistItem)


        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        /*val adapter = RecyclerView.Adapter
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager */


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
        inflater.inflate(R.menu.edit_rule_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = presenter.onMenuItemClick(item.itemId)

    companion object {
        fun newInstance(): RuleCreateFragment = RuleCreateFragment()
    }

    override fun showRuleDetailsActivity() {
        val intent = Intent(this.context, RuleDetailsActivity::class.java)
        startActivity(intent)
    }

    override fun onClick(v: View) {
        throw UnsupportedOperationException("not implemented")
    }


}