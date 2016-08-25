package com.lostincontext.utils

import android.app.Activity
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager


fun Activity.addFragmentToActivity(fragmentManager: FragmentManager,
                                   fragment: Fragment,
                                   @IdRes containerViewId: Int) {
    val transaction = fragmentManager.beginTransaction()
    transaction.add(containerViewId, fragment)
    transaction.commit()
}
