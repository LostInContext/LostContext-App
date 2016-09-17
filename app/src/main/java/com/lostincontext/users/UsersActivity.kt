package com.lostincontext.users

import android.os.Bundle
import com.lostincontext.R
import com.lostincontext.commons.BaseActivity
import com.lostincontext.utils.addFragmentToActivity


class UsersActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.users_screen)


        val fm = supportFragmentManager
        var fragment: UsersFragment? = fm.findFragmentById(R.id.contentFrame) as UsersFragment?

        if (fragment == null) {
            fragment = UsersFragment.newInstance()
            addFragmentToActivity(fm, fragment, R.id.contentFrame)
        }

    }


}
