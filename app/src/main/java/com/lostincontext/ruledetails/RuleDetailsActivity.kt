package com.lostincontext.ruledetails

import android.os.Bundle
import com.lostincontext.R
import com.lostincontext.commons.BaseActivity
import com.lostincontext.utils.addFragmentToActivity


class RuleDetailsActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rule_details_screen)

        val fm = supportFragmentManager
        var fragment: RuleDetailsFragment? = fm.findFragmentById(R.id.contentFrame) as RuleDetailsFragment?
        if (fragment == null) {
            fragment = RuleDetailsFragment.newInstance()
            this.addFragmentToActivity(fm, fragment, R.id.contentFrame)
        }
    }

}