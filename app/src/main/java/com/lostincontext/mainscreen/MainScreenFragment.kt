package com.lostincontext.mainscreen

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lostincontext.R
import com.lostincontext.application.LostApplication
import com.lostincontext.awareness.AwarenessModule
import com.lostincontext.commons.BaseActivity
import com.lostincontext.commons.list.SpacesItemDecoration
import com.lostincontext.commons.list.StatefulAdapter.ContentState
import com.lostincontext.data.playlist.Playlist
import com.lostincontext.data.rulesV2.Rule
import com.lostincontext.databinding.MainScreenFragmentBinding
import com.lostincontext.playlists.PlaylistsContract
import com.lostincontext.rulecreate.RuleCreateActivity
import com.lostincontext.rulecreate.RuleCreateContract.EXTRA_RULE
import com.lostincontext.that.ThatService
import com.lostincontext.utils.logD
import javax.inject.Inject


class MainScreenFragment : Fragment(), MainScreenContract.View {

    @Inject internal lateinit var presenter: MainScreenPresenter

    private lateinit var adapter: MainScreenAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create the presenter
        DaggerMainScreenComponent.builder()
                .mainScreenPresenterModule(MainScreenPresenterModule(this))
                .applicationComponent((activity.application as LostApplication).appComponent)
                .awarenessModule(AwarenessModule(activity as BaseActivity))
                .build()
                .inject(this)

    }


    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<MainScreenFragmentBinding>(inflater!!,
                                                                         R.layout.main_screen_fragment,
                                                                         container,
                                                                         false)
        binding.presenter = presenter


        val recyclerView = binding.recyclerView
        val resources = resources
        val span = resources.getInteger(R.integer.list_span)
        val layoutManager = GridLayoutManager(context, span)
        recyclerView.layoutManager = layoutManager
        val space = resources.getDimensionPixelSize(R.dimen.grid_spacing)
        recyclerView.addItemDecoration(SpacesItemDecoration(space, span))

        adapter = MainScreenAdapter(presenter)

        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                when (adapter.currentState) {
                    ContentState.LOADING,
                    ContentState.ERROR,
                    ContentState.EMPTY -> return span

                    ContentState.CONTENT -> return 1

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

    override fun getPendingIntent(playlist: Playlist): PendingIntent {
        val intent = Intent(this.context, ThatService::class.java)
        return PendingIntent.getService(this.context.applicationContext,
                                        0,
                                        intent,
                                        0)
    }


    override fun openRuleCreationScreen() {
        val intent = Intent(this.context, RuleCreateActivity::class.java)
        startActivityForResult(intent, CREATE_RULE_CODE)
    }

    override fun setRules(rules: List<Rule>) {
        adapter.setRules(rules)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        logD(TAG) { "onActivityResult : requestCode $requestCode, resultCode $resultCode, intent $data " }
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) return
        when (requestCode) {
            CREATE_RULE_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val rule = data.getParcelableExtra<Rule>(EXTRA_RULE)
                    presenter.onRuleInput(rule)
                }


            }
        }

    }

    override fun getPendingIntentFor(playlist: Playlist): PendingIntent {
        val intent = Intent(this.context, ThatService::class.java)
        return PendingIntent.getService(this.context.applicationContext,
                                        0,
                                        intent,
                                        0)
    }


    companion object {
        private val TAG: String = MainScreenFragment::class.java.simpleName
        fun newInstance(): MainScreenFragment = MainScreenFragment()
    }

}