package com.lostincontext.condition

import android.os.Bundle
import com.lostincontext.awareness.Awareness
import com.lostincontext.data.location.repo.LocationRepository
import javax.inject.Inject


class ConditionPresenter : ConditionContract.Presenter {

    private val view: ConditionContract.View
    private val locationRepository: LocationRepository
    private val awareness: Awareness

    @Inject internal constructor(view: ConditionContract.View,
                                 icicle: Bundle?,
                                 locationRepository: LocationRepository,
                                 awareness: Awareness) {
        this.view = view
        this.locationRepository = locationRepository
        this.awareness = awareness

        if (icicle != null) {
            // todo fetch saved state here
        }
    }


    override fun start() {

    }


    override fun onPlusButtonClick() {

    }

    override fun onMenuItemClick(itemId: Int): Boolean {
        return true
    }


}