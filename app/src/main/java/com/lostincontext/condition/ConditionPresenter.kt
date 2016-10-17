package com.lostincontext.condition

import android.os.Bundle
import com.lostincontext.R
import com.lostincontext.awareness.Awareness
import com.lostincontext.commons.list.Section
import com.lostincontext.condition.pick.BottomSheetItemSection
import com.lostincontext.condition.pick.GridBottomSheetItem
import com.lostincontext.data.location.repo.LocationRepository
import com.lostincontext.ruledetails.RuleDetailsPresenter
import java.util.*
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


    override fun onPlusButtonClick() = view.displayFenceChoice()

    override fun onMenuItemClick(itemId: Int): Boolean {
        return true
    }

    override fun provideFenceChoices(): List<Section<*>> {
        val choices = arrayListOf(GridBottomSheetItem("Walking",
                                                      R.drawable.ic_walk_24,
                                                      RuleDetailsPresenter.Picker.WALK),
                                  GridBottomSheetItem("Running",
                                                      R.drawable.ic_run_24,
                                                      RuleDetailsPresenter.Picker.RUN),
                                  GridBottomSheetItem("On bicycle",
                                                      R.drawable.ic_bike_24,
                                                      RuleDetailsPresenter.Picker.BIKE),
                                  GridBottomSheetItem("In vehicle",
                                                      R.drawable.ic_car_24,
                                                      RuleDetailsPresenter.Picker.CAR),
                                  GridBottomSheetItem("Plugged in",
                                                      R.drawable.ic_headset_24,
                                                      RuleDetailsPresenter.Picker.PLUG_IN),
                                  GridBottomSheetItem("Plugged out",
                                                      R.drawable.ic_headset_24,
                                                      RuleDetailsPresenter.Picker.PLUG_OUT),
                                  GridBottomSheetItem("At home",
                                                      R.drawable.ic_home_24,
                                                      RuleDetailsPresenter.Picker.HOME),
                                  GridBottomSheetItem("At work",
                                                      R.drawable.ic_work_24,
                                                      RuleDetailsPresenter.Picker.WORK))

        val fencesSection = BottomSheetItemSection("Pick a condition", choices)

        return Arrays.asList<Section<*>>(fencesSection)
    }

    override fun onGridBottomSheetItemClick(item: GridBottomSheetItem) {

    }


}