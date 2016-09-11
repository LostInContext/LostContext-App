package com.lostincontext.mainscreen

import android.os.Bundle
import com.lostincontext.R
import com.lostincontext.commons.BaseActivity
import com.lostincontext.utils.addFragmentToActivity


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


    }


}
