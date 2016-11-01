package com.lostincontext.rulecreate

import android.os.Bundle
import com.lostincontext.R
import com.lostincontext.commons.BaseActivity
import com.lostincontext.utils.addFragmentToActivity


class RuleCreateActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rule_create_screen)

        val fm = supportFragmentManager
        var fragment: RuleCreateFragment? = fm.findFragmentById(R.id.contentFrame) as RuleCreateFragment?
        if (fragment == null) {
            fragment = RuleCreateFragment.newInstance()
            this.addFragmentToActivity(fm, fragment, R.id.contentFrame)
        }
    }

}