package com.lostincontext.mainscreen

import android.os.Bundle
import com.lostincontext.R
import com.lostincontext.application.LostApplication
import com.lostincontext.awareness.AwarenessModule
import com.lostincontext.commons.BaseActivity
import com.lostincontext.utils.addFragmentToActivity
import javax.inject.Inject


class MainActivity : BaseActivity() {

    @Inject internal lateinit var mainScreenPresenter: MainScreenPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen)


        val fm = supportFragmentManager
        var fragment: MainScreenFragment? = fm.findFragmentById(R.id.contentFrame) as MainScreenFragment?

        if (fragment == null) {
            fragment = MainScreenFragment.newInstance()
            addFragmentToActivity(fm, fragment, R.id.contentFrame)
        }

        // Create the presenter
        DaggerMainScreenComponent.builder()
                .mainScreenPresenterModule(MainScreenPresenterModule(fragment))
                .applicationComponent((application as LostApplication).appComponent)
                .awarenessModule(AwarenessModule(this))
                .build()
                .inject(this)
    }


}
