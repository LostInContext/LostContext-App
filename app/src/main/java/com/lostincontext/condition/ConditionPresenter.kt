package com.lostincontext.condition

import android.os.Bundle
import com.genius.groupie.UpdatingGroup
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
import com.lostincontext.rulecreate.ConditionItem
import com.lostincontext.ruledetails.RuleDetailsContract
import com.lostincontext.ruledetails.RuleDetailsPresenter
import com.lostincontext.ruledetails.items.FenceItem
import java.util.*
import javax.inject.Inject


class ConditionPresenter : ConditionContract.Presenter {


    private val view: ConditionContract.View
    private val locationRepository: LocationRepository
    private val awareness: Awareness
    private val items = ArrayList<ConditionItem>()

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
        view.setItems(items)

    }

    override fun onLinkClick(item: FenceItem) {
        toggleLink(item)
    }

    fun toggleLink(item: FenceItem) {
//        when (item.link) {
//            AtomicCondition.Modifier.NONE -> item.link = FenceItem.Link.AND
//            AtomicCondition.Modifier.NOT -> item.link = FenceItem.Link.AND_NOT
//            FenceItem.Link.AND_NOT -> item.link = FenceItem.Link.OR_NOT
//            FenceItem.Link.OR_NOT -> item.link = FenceItem.Link.AND
//            FenceItem.Link.WHEN -> throw  RuntimeException("unexpected")
//        }
//        view.notifyItemChanged(items.indexOf(item), RuleDetailsContract.LINK_CHANGED)
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

        val fencesSection = BottomSheetItemSection("Add a rule…", choices)

        return Arrays.asList<Section<*>>(fencesSection)
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
                val conditionItem = ConditionItem(this,
                                                  items.size,
                                                  Condition(listOf(atomicCondition)),"")
                items.add(conditionItem)
                view.notifyItemInserted(conditionItem)
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

        val atomicCondition = AtomicCondition(fenceVM, AtomicCondition.Modifier.NONE)
        val conditionItem = ConditionItem(this, items.size, Condition(listOf(atomicCondition)), "")
        items.add(conditionItem)
        view.notifyItemInserted(conditionItem)

    }

    override fun onDeleteButtonClick(condition: Condition) {
        //todo
    }

    override fun onConditionClick(condition: Condition) {
        //todo
    }
}