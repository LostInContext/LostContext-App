package com.lostincontext.ruledetails

import android.os.Bundle
import android.text.TextUtils
import com.google.android.gms.awareness.fence.FenceUpdateRequest
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallbacks
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.model.LatLng
import com.google.common.collect.Lists.newArrayList
import com.lostincontext.R
import com.lostincontext.awareness.Awareness
import com.lostincontext.commons.list.Section
import com.lostincontext.ruledetails.pick.GridBottomSheetItem
import com.lostincontext.data.location.LocationModel
import com.lostincontext.data.location.repo.LocationRepository
import com.lostincontext.data.location.repo.LocationRepository.LocationCallback
import com.lostincontext.data.playlist.Playlist
import com.lostincontext.data.rules.*
import com.lostincontext.data.rules.CompositeFenceVM.Operator
import com.lostincontext.data.rules.repo.RulesRepository
import com.lostincontext.ruledetails.RuleDetailsContract.LINK_CHANGED
import com.lostincontext.ruledetails.RuleDetailsContract.Presenter
import com.lostincontext.ruledetails.RuleDetailsContract.RuleErrors
import com.lostincontext.ruledetails.RuleDetailsContract.View
import com.lostincontext.ruledetails.items.FenceItem
import com.lostincontext.ruledetails.items.FenceItem.Link
import com.lostincontext.ruledetails.items.FenceItem.Link.*
import com.lostincontext.ruledetails.pick.BottomSheetItemSection
import com.lostincontext.utils.logD
import com.lostincontext.utils.logE
import java.util.*
import java.util.EnumSet.of
import javax.inject.Inject

class RuleDetailsPresenter
@Inject internal constructor(private val view: View,
                             private val locationRepository: LocationRepository,
                             private val rulesRepository: RulesRepository,
                             private val awareness: Awareness)
: Presenter,
  GoogleApiClient.ConnectionCallbacks,
  GoogleApiClient.OnConnectionFailedListener {

    private val name = ""
    private val items = ArrayList<FenceItem>()
    private var playlist: Playlist? = null

    @Inject internal fun setup() {
        view.setPresenter(this)
        awareness.init(this,
                       this)
    }


    override fun start() {
        view.setItems(items)
        view.setRuleName(name)
    }

    override fun onLinkClick(item: FenceItem) {
        toggleLink(item)
    }

    fun toggleLink(item: FenceItem) {
        when (item.link) {
            AND -> item.link = Link.OR
            FenceItem.Link.OR -> item.link = AND_NOT
            AND_NOT -> item.link = OR_NOT
            OR_NOT -> item.link = AND
            WHEN -> {
            }
        }
        view.notifyItemChanged(items.indexOf(item), LINK_CHANGED)
    }

    override fun onPlusButtonClick() {
        view.displayFenceChoice()
    }

    override fun onConnected(bundle: Bundle?) {
        logD(TAG) { "onConnected" }
    }

    override fun onConnectionSuspended(i: Int) {
        logD(TAG) { "onConnectionSuspended " + i }
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        logE(TAG) { "onConnectionSuspended " + connectionResult.errorMessage }
    }

    enum class Picker {
        WALK,
        RUN,
        BIKE,
        CAR,
        PLUG_IN,
        PLUG_OUT,
        HOME,
        WORK,
        PLAYLIST
    }

    override fun provideFenceChoices(): List<Section<*>> {

        val choices = newArrayList(GridBottomSheetItem("Walking",
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

        val fencesSection = BottomSheetItemSection("Pick a condition", choices)


        val playlistPickers = newArrayList(GridBottomSheetItem("Playlist",
                                                               R.drawable.ic_music_note_24,
                                                               Picker.PLAYLIST))

        val mediaPickSection = BottomSheetItemSection("Pick a playlist", playlistPickers)

        return Arrays.asList<Section<*>>(fencesSection,
                                         mediaPickSection)
    }


    override fun onGridBottomSheetItemClick(item: GridBottomSheetItem) {
        when (item.picker) {

            RuleDetailsPresenter.Picker.WALK,
            RuleDetailsPresenter.Picker.RUN,
            RuleDetailsPresenter.Picker.BIKE,
            RuleDetailsPresenter.Picker.CAR,
            RuleDetailsPresenter.Picker.PLUG_IN,
            RuleDetailsPresenter.Picker.PLUG_OUT -> {
                val fenceItem = FenceItem.createFromPick(item,
                                                         getFenceVMForPick(item),
                                                         items.isEmpty())
                items.add(fenceItem)
                view.notifyItemInserted(items.indexOf(fenceItem))
            }

            RuleDetailsPresenter.Picker.HOME,
            RuleDetailsPresenter.Picker.WORK -> handleLocationItemClick(item)

            RuleDetailsPresenter.Picker.PLAYLIST -> view.pickAPlaylist()
        }
    }


    private fun handleLocationItemClick(item: GridBottomSheetItem) {
        val name = item.picker.name
        locationRepository.getLocation(name, object : LocationCallback {
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
        val fenceItem = FenceItem.createFromPick(item, fenceVM, items.isEmpty())
        items.add(fenceItem)
        view.notifyItemInserted(items.indexOf(fenceItem))

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

    override fun onPlaylistPicked(playlist: Playlist) {
        this.playlist = playlist
        view.showPlaylist(playlist)
    }

    override fun onPlacePicked(placeName: String, item: GridBottomSheetItem, latLng: LatLng) {
        locationRepository.saveLocation(placeName, latLng)
        addLocationFence(item, LocationModel(placeName,
                                             latLng.latitude,
                                             latLng.longitude))
    }


    override fun onMenuItemClick(itemId: Int): Boolean {
        when (itemId) {
            R.id.action_save -> {
                saveRuleAndQuit()
                return true
            }
        }
        return false
    }


    private fun saveRuleAndQuit() {
        val errors = EnumSet.noneOf(RuleErrors::class.java)
        if (items.isEmpty()) errors.add(RuleErrors.NO_CONDITION)
        if (playlist == null) errors.add(RuleErrors.NO_PLAYLIST)
        if (TextUtils.isEmpty(name)) errors.add(RuleErrors.NO_TITLE)

        if (!errors.isEmpty()) {
            view.showSnack(errors)
            return
        }

        val fenceVM = extractFenceForRule()
        val rule = Rule(name, fenceVM, playlist!!)

        val builder = FenceUpdateRequest.Builder()
        builder.addFence(rule.name,
                         rule.fenceVM.build(FenceBuilder()),
                         view.getPendingIntentFor(rule.playlist))
        awareness.updateFence(builder.build()).setResultCallback(object : ResultCallbacks<Status>() {
            override fun onSuccess(status: Status) {
                logD(TAG) { "updateFence.onSuccess: " + status.statusMessage }
                rulesRepository.saveRule(rule)
                view.finishActivity()
            }

            override fun onFailure(status: Status) {
                view.showSnack(of(RuleErrors.SAVE_ERROR))
            }
        })

    }


    private fun extractFenceForRule(): FenceVM {
        var completedFence: FenceVM? = null
        var fenceToAccumulate: FenceVM
        val list = cleanedList
        var i = 0
        val count = list.size
        while (i < count) {
            val item = list[i]

            // first, we regroup the next compatible fences together in a composite blob :
            var j = i + 1
            val similarItems = ArrayList<FenceVM>()
            var link: Link? = null
            while (j < count) {
                val nextItem = list[j]
                if (item.link == WHEN && (link == null || link == nextItem.link) || nextItem.link == item.link) {
                    similarItems.add(nextItem.fenceVM)
                    link = nextItem.link
                    j++
                } else
                    break
            }

            if (similarItems.isEmpty())
                fenceToAccumulate = item.fenceVM
            else {
                i = j - 1
                similarItems.add(0, item.fenceVM)
                val op = if (link == Link.OR) Operator.OR else Operator.AND
                fenceToAccumulate = CompositeFenceVM(similarItems, op)
            }

            // then we assemble with the existing result :

            if (completedFence == null)
                completedFence = fenceToAccumulate
            else {
                val op = if (link == Link.OR) Operator.OR else Operator.AND
                completedFence = CompositeFenceVM(Arrays.asList<FenceVM>(completedFence,
                                                                         fenceToAccumulate), op)
            }
            i++

        }
        return completedFence!!
    }

    /**
     * @return a new list, where there are only [Link.AND], [Link.OR] & [Link.WHEN]
     * * links, all the [Link.AND_NOT] & [Link.OR_NOT] see their fenceVMs wrapped in
     * * [com.lostincontext.data.rules.NotFenceVM]s.
     */
    private val cleanedList: List<FenceItem>
        get() {
            val cleanedItems = ArrayList<FenceItem>(items.size)

            for (item in items) {
                cleanedItems.add(FenceItem.wrapNot(item))
            }
            return cleanedItems
        }

    companion object {
        private val TAG = RuleDetailsPresenter::class.java.simpleName
    }
}
