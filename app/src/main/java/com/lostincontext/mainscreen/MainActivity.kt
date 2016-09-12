package com.lostincontext.mainscreen

import android.os.Bundle
import com.lostincontext.R
import com.lostincontext.application.LostApplication
import com.lostincontext.commons.BaseActivity
import com.lostincontext.utils.addFragmentToActivity
import com.lostincontext.utils.logD


class MainActivity : BaseActivity() {

    val TAG = "MainActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen)


        val fm = supportFragmentManager
        var fragment: MainScreenFragment? = fm.findFragmentById(R.id.contentFrame) as MainScreenFragment?

        if (fragment == null) {
            fragment = MainScreenFragment.newInstance()
            addFragmentToActivity(fm, fragment, R.id.contentFrame)
        }

        val lostApplication = application as LostApplication
        val userRepository = lostApplication.appComponent.userRepository
        userRepository.queryUsers("angie23", {
            logD(TAG) { it.body().toString() }
        }, { logD(TAG) { it.toString().toString() } })

    }


}
