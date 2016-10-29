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
import com.lostincontext.ruledetails.RuleDetailsPresenter
import java.util.*
import javax.inject.Inject


class ConditionPresenter
@Inject internal constructor(private val view: ConditionContract.View, icicle: Bundle?,
                             private val locationRepository: LocationRepository,
                             private val awareness: Awareness) : ConditionContract.Presenter {


    private val items = ArrayList<AtomicCondition>()

    init {
        icicle?.let {  /* todo fetch saved state here */ }
    }

    override fun start() {
        //view.setItems(items)
    }

    override fun onPlusButtonClick() = view.displayFenceChoice()

    override fun onMenuItemClick(itemId: Int): Boolean {
        return true
    }

    override fun onDeleteButtonClick(atomic: AtomicCondition) {
        items.remove(atomic)
        view.remove(atomic)
    }

    override fun onToggleClick(atomic: AtomicCondition) {
        atomic.toggle()
        view.notifyChange(atomic)
    }


    //region fence pick dialog

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

            RuleDetailsPresenter.Picker.WALK,
            RuleDetailsPresenter.Picker.RUN,
            RuleDetailsPresenter.Picker.BIKE,
            RuleDetailsPresenter.Picker.CAR,
            RuleDetailsPresenter.Picker.PLUG_IN,
            RuleDetailsPresenter.Picker.PLUG_OUT -> {

                val atomicCondition = AtomicCondition(getFenceVMForPick(item),
                                                      AtomicCondition.Modifier.NONE)
                addCondition(atomicCondition, item)

            }

            RuleDetailsPresenter.Picker.HOME,
            RuleDetailsPresenter.Picker.WORK -> handleLocationItemClick(item)
        }
    }

    private fun getFenceVMForPick(pick: GridBottomSheetItem): FenceVM {
        when (pick.picker) {

            RuleDetailsPresenter.Picker.WALK ->
                return DetectedActivityFenceVM(DetectedActivityFenceVM.Type.WALKING,
                                               DetectedActivityFenceVM.State.DURING)

            RuleDetailsPresenter.Picker.RUN ->
                return DetectedActivityFenceVM(DetectedActivityFenceVM.Type.RUNNING,
                                               DetectedActivityFenceVM.State.DURING)

            RuleDetailsPresenter.Picker.BIKE ->
                return DetectedActivityFenceVM(DetectedActivityFenceVM.Type.ON_BICYCLE,
                                               DetectedActivityFenceVM.State.DURING)

            RuleDetailsPresenter.Picker.CAR ->
                return DetectedActivityFenceVM(DetectedActivityFenceVM.Type.IN_VEHICLE,
                                               DetectedActivityFenceVM.State.DURING)

            RuleDetailsPresenter.Picker.PLUG_IN -> return HeadphoneFenceVM(HeadphoneFenceVM.State.PLUGGED_IN)

            RuleDetailsPresenter.Picker.PLUG_OUT -> return HeadphoneFenceVM(HeadphoneFenceVM.State.PLUGGED_OUT)

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

}