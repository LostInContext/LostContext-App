package com.lostincontext.condition

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.checkSelfPermission
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.*
import com.genius.groupie.GroupAdapter
import com.genius.groupie.UpdatingGroup
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.location.places.ui.PlacePicker
import com.lostincontext.R
import com.lostincontext.application.LostApplication
import com.lostincontext.awareness.AwarenessModule
import com.lostincontext.commons.BaseActivity
import com.lostincontext.condition.pick.GridBottomSheetItem
import com.lostincontext.data.rules.FenceNamer
import com.lostincontext.data.rulesV2.AtomicCondition
import com.lostincontext.databinding.ConditionScreenFragmentBinding
import com.lostincontext.rulecreate.AtomicConditionItem
import com.lostincontext.ruledetails.ConditionPresenterModule
import com.lostincontext.ruledetails.PickerDialogFragment
import com.lostincontext.utils.logD
import java.util.*
import javax.inject.Inject


class ConditionFragment : Fragment(), ConditionContract.View {


    @Inject lateinit internal var presenter: ConditionPresenter

    private lateinit var binding: ConditionScreenFragmentBinding

    private lateinit var adapter: GroupAdapter

    private lateinit var namer: FenceNamer

    private var savedPlaceName: String? = null
    private var savedGridBottomSheetItem: GridBottomSheetItem? = null
    private val group: UpdatingGroup = UpdatingGroup()
    private var items: ArrayList<AtomicConditionItem> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create the presenter
        DaggerConditionComponent.builder()
                .conditionPresenterModule(ConditionPresenterModule(this, savedInstanceState))
                .applicationComponent((activity.application as LostApplication).appComponent)
                .awarenessModule(AwarenessModule(activity as BaseActivity))
                .build()
                .inject(this)

        namer = FenceNamer(context)

        val manager = fragmentManager
        val pickerFragment = manager.findFragmentByTag(PickerDialogFragment.TAG) as PickerDialogFragment?
        if (pickerFragment != null) {
            pickerFragment.registerCallback(presenter)
            pickerFragment.setSections(presenter.provideFenceChoices())
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.start()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<ConditionScreenFragmentBinding>(inflater,
                                                                          R.layout.condition_screen_fragment,
                                                                          container,
                                                                          false)

        val recyclerView = binding.recyclerView

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        adapter = GroupAdapter()
        adapter.add(group)


        binding.plusButton.callback = presenter
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)

        val activity: AppCompatActivity = activity as AppCompatActivity
        activity.supportActionBar?.title = resources.getString(R.string.condition_title,
                                                               34) // todo condition number

        return binding.root
    }

    override fun remove(atomic: AtomicCondition) {
        val item = items.find { it.atomic == atomic }
        items.remove(item)
        group.update(items)
    }

    override fun add(atomicCondition: AtomicCondition, isFirst: Boolean) {
        logD(TAG) { "add : $atomicCondition, is first ? $isFirst" }
        val item = AtomicConditionItem(presenter,
                                       atomicCondition,
                                       isFirst,
                                       namer)
        items.add(item)
        group.update(items)
    }

    override fun notifyChange(atomic: AtomicCondition) {
        val position = items.indexOfFirst { it.atomic == atomic }
        group.notifyItemChanged(position)
    }

    override fun displayFenceChoice() {
        val picker = PickerDialogFragment.newInstance()
        picker.registerCallback(presenter)
        picker.setSections(presenter.provideFenceChoices())
        picker.show(fragmentManager, PickerDialogFragment.TAG)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
        inflater.inflate(R.menu.condition_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = presenter.onMenuItemClick(item.itemId)

    override fun checkPermissionsAndShowLocationPicker(name: String, item: GridBottomSheetItem) {
        savedPlaceName = name
        savedGridBottomSheetItem = item
        checkLocationPermissionAndShowPicker()
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE
                && grantResults.size == 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            showLocationPicker()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (requestCode == LOCATION_PICKER_REQUEST_CODE
                    && resultCode == Activity.RESULT_OK
                    && !TextUtils.isEmpty(savedPlaceName)) {
                val place = PlacePicker.getPlace(context, data)
                val latLng = place.latLng
                presenter.onPlacePicked(savedPlaceName!!, savedGridBottomSheetItem!!, latLng)
            } else if (requestCode == PLAY_SERVICES_REQUEST_CODE
                    && resultCode == Activity.RESULT_OK
                    && !TextUtils.isEmpty(savedPlaceName)) {
                showLocationPicker()
            }
        }
    }

    private fun showLocationPicker() {
        try {
            val builder = PlacePicker.IntentBuilder()
            val intent = builder.build(activity)
            startActivityForResult(intent, LOCATION_PICKER_REQUEST_CODE)
        } catch (e: GooglePlayServicesRepairableException) {
            val dialog = GoogleApiAvailability.getInstance().getErrorDialog(activity,
                                                                            e.connectionStatusCode,
                                                                            PLAY_SERVICES_REQUEST_CODE)
            dialog.show()
        } catch (e: GooglePlayServicesNotAvailableException) {
            Snackbar.make(binding.root,
                          R.string.play_services_not_available,
                          Snackbar.LENGTH_LONG)
                    .show()
        }
    }

    private fun checkLocationPermissionAndShowPicker() {
        val permissionResult = checkSelfPermission(context,
                                                   Manifest.permission.ACCESS_FINE_LOCATION)
        val permissionGranted = permissionResult == PackageManager.PERMISSION_GRANTED
        if (permissionGranted) showLocationPicker()
        else {
            logD(ConditionFragment.TAG) { "getLocationPermission: permissionNeeded" }
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                Snackbar.make(binding.root,
                              R.string.permission_rationale,
                              Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.ok) {
                            requestPermissions(FINE_LOCATION_ARRAY,
                                               LOCATION_PICKER_REQUEST_CODE)
                        }
                        .show()

            } else {
                requestPermissions(FINE_LOCATION_ARRAY,
                                   LOCATION_PERMISSION_REQUEST_CODE)
            }
        }

    }

    companion object {
        private val TAG = ConditionFragment::class.java.simpleName

        private val LOCATION_PICKER_REQUEST_CODE = 9002
        private val LOCATION_PERMISSION_REQUEST_CODE = 9003
        private val PLAY_SERVICES_REQUEST_CODE = 9004

        private val FINE_LOCATION_ARRAY = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        fun newInstance(): ConditionFragment = ConditionFragment()
    }
}

