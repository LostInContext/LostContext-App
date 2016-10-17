package com.lostincontext.condition

import android.os.Bundle
import com.lostincontext.R
import com.lostincontext.commons.BaseActivity
import com.lostincontext.utils.addFragmentToActivity


class ConditionActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen)


        val fm = supportFragmentManager
        var fragment: ConditionFragment? = fm.findFragmentById(R.id.contentFrame) as ConditionFragment?

        if (fragment == null) {
            fragment = ConditionFragment.newInstance()
            addFragmentToActivity(fm, fragment, R.id.contentFrame)
        }

    }

}
