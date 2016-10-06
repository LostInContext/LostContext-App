package com.lostincontext.rulecreate

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.lostincontext.R
import com.lostincontext.application.LostApplication
import com.lostincontext.awareness.AwarenessModule
import com.lostincontext.commons.BaseActivity
import com.lostincontext.databinding.RuleCreateScreenFragmentBinding
import com.lostincontext.ruledetails.RuleDetailsActivity
import javax.inject.Inject

class RuleCreateFragment : Fragment(), RuleCreateContract.View {

    @Inject lateinit internal var presenter: RuleCreatePresenter
    private lateinit var binding: RuleCreateScreenFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        DaggerRuleCreateComponent.builder()
                .ruleCreatePresenterModule(RuleCreatePresenterModule(this,savedInstanceState))
                .applicationComponent((activity.application as LostApplication).appComponent)
                .awarenessModule(AwarenessModule(activity as BaseActivity))
                .build()
                .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<RuleCreateScreenFragmentBinding>(inflater!!,
                                                                           R.layout.rule_create_screen_fragment,
                                                                           container,
                                                                           false)
        binding.plusButton.callback = presenter

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
        fun newInstance(): RuleCreateFragment {
            return RuleCreateFragment()
        }
    }

    override fun showRuleDetailsActivity() {
        val intent = Intent(this.context, RuleDetailsActivity::class.java)
        startActivity(intent)
    }

}