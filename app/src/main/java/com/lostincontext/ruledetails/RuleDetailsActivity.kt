package com.lostincontext.ruledetails

import android.os.Bundle
import com.lostincontext.R
import com.lostincontext.application.LostApplication
import com.lostincontext.awareness.AwarenessModule
import com.lostincontext.commons.BaseActivity
import com.lostincontext.utils.addFragmentToActivity
import javax.inject.Inject


class RuleDetailsActivity : BaseActivity() {


    @Inject lateinit internal var presenter: RuleDetailsPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rule_details_screen)

        val fm = supportFragmentManager
        var fragment: RuleDetailsFragment? = fm.findFragmentById(R.id.contentFrame) as RuleDetailsFragment?
        if (fragment == null) {
            fragment = RuleDetailsFragment.newInstance()
            this.addFragmentToActivity(fm, fragment, R.id.contentFrame)
        }

        DaggerRuleDetailsComponent.builder()
                .ruleDetailsPresenterModule(RuleDetailsPresenterModule(fragment))
                .applicationComponent((application as LostApplication).appComponent)
                .awarenessModule(AwarenessModule(this))
                .build()
                .inject(this)

    }

}