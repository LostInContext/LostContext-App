package com.lostincontext.condition

import android.os.Bundle
import com.google.android.gms.maps.model.LatLng
import com.lostincontext.R
import com.lostincontext.awareness.Awareness
import com.lostincontext.commons.list.Section
import com.lostincontext.condition.pick.BottomSheetItemSection
import com.lostincontext.condition.pick.GridBottomSheetItem
import com.lostincontext.data.location.LocationModel
import com.lostincontext.data.location.repo.LocationRepository
import com.lostincontext.data.rules.DetectedActivityFenceVM
import com.lostincontext.data.rules.FenceVM
import com.lostincontext.data.rules.HeadphoneFenceVM
import com.lostincontext.data.rules.LocationFenceVM
import com.lostincontext.data.rulesV2.AtomicCondition
import com.lostincontext.data.rulesV2.Condition
import com.lostincontext.ruledetails.RuleDetailsPresenter.Picker
import java.util.*
import javax.inject.Inject


class ConditionPresenter
@Inject internal constructor(private val view: ConditionContract.View,
                             icicle: Bundle?,
                             private val locationRepository: LocationRepository,
                             private val awareness: Awareness) : ConditionContract.Presenter {


    private var items: MutableList<AtomicCondition>
    private var condition: Condition

    init {
        if (icicle == null) {
            items = ArrayList()
            condition = Condition(items)
        } else {
            condition = icicle.getParcelable(KEY_CONDITION)
            items = condition.atomics
        }
    }

    override fun start() {
        view.setupCondition(condition)
    }

    override fun saveState(outState: Bundle) {
        outState.putParcelable(KEY_CONDITION, condition)
    }

    override fun onPlusButtonClick() = view.displayFenceChoice()

    override fun onMenuItemClick(itemId: Int): Boolean {
        return true
    }

    override fun onDeleteButtonClick(atomic: AtomicCondition) {
        items.remove(atomic)
        view.remove(atomic)

        val picker = getPickerForAtomic(atomic)

        val find = choices.find { it.picker == picker }
        find?.isPicked?.set(false)

    }

    private fun getPickerForAtomic(atomic: AtomicCondition): Picker {
        val fence = atomic.fence

        when (fence) {
            is DetectedActivityFenceVM -> when (fence.type) {
                DetectedActivityFenceVM.Type.RUNNING -> return Picker.RUN
                DetectedActivityFenceVM.Type.WALKING -> return Picker.WALK
                DetectedActivityFenceVM.Type.IN_VEHICLE -> return Picker.CAR
                DetectedActivityFenceVM.Type.ON_BICYCLE -> return Picker.BIKE
            }

            is HeadphoneFenceVM -> when (fence.state) {
                HeadphoneFenceVM.State.PLUGGED_IN -> return Picker.PLUG_IN
                HeadphoneFenceVM.State.PLUGGED_OUT -> return Picker.PLUG_OUT
            }

            is LocationFenceVM -> when (fence.name) {
                LocationFenceVM.HOME -> return Picker.HOME
                LocationFenceVM.WORK -> return Picker.WORK
            }

        }
        throw IllegalArgumentException("missing picker")
    }

    override fun onToggleClick(atomic: AtomicCondition) {
        atomic.toggle()
        view.notifyChange(atomic)
    }


    //region fence pick dialog

    val choices = arrayListOf(GridBottomSheetItem("Walking",
                                                  R.drawable.ic_walk_24,
                                                  Picker.WALK),
                              GridBottomSheetItem("Running",
                                                  R.drawable.ic_run_24,
                                                  Picker.RUN),
                              GridBottomSheetItem("On bicycle",
                                                  R.drawable.ic_bike_24,
                                                  Picker.BIKE),
                              GridBottomSheetItem("In vehicle",
                                                  R.drawable.ic_car_24,
                                                  Picker.CAR),
                              GridBottomSheetItem("Plugged in",
                                                  R.drawable.ic_headset_24,
                                                  Picker.PLUG_IN),
                              GridBottomSheetItem("Plugged out",
                                                  R.drawable.ic_headset_24,
                                                  Picker.PLUG_OUT),
                              GridBottomSheetItem("At home",
                                                  R.drawable.ic_home_24,
                                                  Picker.HOME),
                              GridBottomSheetItem("At work",
                                                  R.drawable.ic_work_24,
                                                  Picker.WORK))

    val fencesSection = BottomSheetItemSection("Add a rule…", choices)
    val choicesList = Arrays.asList<Section<*>>(fencesSection)

    override fun provideFenceChoices(): List<Section<*>> {
        return choicesList
    }

    override fun onPlacePicked(placeName: String, item: GridBottomSheetItem, latLng: LatLng) {
        locationRepository.saveLocation(placeName, latLng)
        addLocationFence(item, LocationModel(placeName,
                                             latLng.latitude,
                                             latLng.longitude))
    }

    override fun onGridBottomSheetItemClick(item: GridBottomSheetItem) {
        when (item.picker) {

            Picker.WALK,
            Picker.RUN,
            Picker.BIKE,
            Picker.CAR,
            Picker.PLUG_IN,
            Picker.PLUG_OUT -> {

                val atomicCondition = AtomicCondition(getFenceVMForPick(item),
                                                      AtomicCondition.Modifier.NONE)
                addCondition(atomicCondition, item)

            }

            Picker.HOME,
            Picker.WORK -> handleLocationItemClick(item)
        }
    }

    private fun getFenceVMForPick(pick: GridBottomSheetItem): FenceVM {
        when (pick.picker) {

            Picker.WALK ->
                return DetectedActivityFenceVM(DetectedActivityFenceVM.Type.WALKING,
                                               DetectedActivityFenceVM.State.DURING)

            Picker.RUN ->
                return DetectedActivityFenceVM(DetectedActivityFenceVM.Type.RUNNING,
                                               DetectedActivityFenceVM.State.DURING)

            Picker.BIKE ->
                return DetectedActivityFenceVM(DetectedActivityFenceVM.Type.ON_BICYCLE,
                                               DetectedActivityFenceVM.State.DURING)

            Picker.CAR ->
                return DetectedActivityFenceVM(DetectedActivityFenceVM.Type.IN_VEHICLE,
                                               DetectedActivityFenceVM.State.DURING)

            Picker.PLUG_IN -> return HeadphoneFenceVM(HeadphoneFenceVM.State.PLUGGED_IN)

            Picker.PLUG_OUT -> return HeadphoneFenceVM(HeadphoneFenceVM.State.PLUGGED_OUT)

            else -> throw RuntimeException("surprise !")
        }

    }

    private fun handleLocationItemClick(item: GridBottomSheetItem) {
        val name = item.picker.name
        locationRepository.getLocation(name, object : LocationRepository.LocationCallback {
            override fun onLocationFetched(locationModel: LocationModel) {
                addLocationFence(item, locationModel)
            }

            override fun onLocationLoadFailed(name: String) {
                view.checkPermissionsAndShowLocationPicker(name, item)
            }
        })
    }

    private fun addLocationFence(item: GridBottomSheetItem, locationModel: LocationModel) {
        val fenceVM = LocationFenceVM(locationModel.placeName, locationModel.getLatLng())

        val atomicCondition = AtomicCondition(fenceVM)
        addCondition(atomicCondition, item)

    }

    private fun addCondition(atomicCondition: AtomicCondition,
                             item: GridBottomSheetItem) {
        item.isPicked.set(true)
        items.add(atomicCondition)
        view.add(atomicCondition, items.size == 1)
    }

    //endregion


    companion object {
        const val KEY_CONDITION = "condition_conditionPresenter"
    }
}