package com.lostincontext.ruledetails

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Activity.RESULT_OK
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.checkSelfPermission
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils.isEmpty
import android.view.*
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.location.places.ui.PlacePicker
import com.lostincontext.R
import com.lostincontext.data.GridBottomSheetItem
import com.lostincontext.data.playlist.Playlist
import com.lostincontext.data.rules.Rule
import com.lostincontext.databinding.RuleDetailsScreenFragmentBinding
import com.lostincontext.playlists.PlaylistsActivity
import com.lostincontext.playlists.PlaylistsContract
import com.lostincontext.ruledetails.RuleDetailsContract.RuleErrors
import com.lostincontext.ruledetails.items.FenceItem
import com.lostincontext.that.ThatService
import com.lostincontext.utils.logD
import java.util.*


class RuleDetailsFragment : Fragment(), RuleDetailsContract.View {


    private lateinit var presenter: RuleDetailsContract.Presenter
    private lateinit var binding: RuleDetailsScreenFragmentBinding
    private lateinit var adapter: RuleDetailsAdapter

    private var savedPlaceName: String? = null
    private var savedGridBottomSheetItem: GridBottomSheetItem? = null

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<RuleDetailsScreenFragmentBinding>(inflater!!,
                                                                            R.layout.rule_details_screen_fragment,
                                                                            container,
                                                                            false)

        val recyclerView = binding.recyclerView

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        adapter = RuleDetailsAdapter(presenter)

        binding.plusButton.callback = presenter
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun setPresenter(presenter: RuleDetailsContract.Presenter) {
        this.presenter = presenter
        val manager = fragmentManager
        val pickerFragment = manager.findFragmentByTag(PickerDialogFragment.TAG) as PickerDialogFragment?
        if (pickerFragment != null) {
            pickerFragment.registerCallback(presenter)
            pickerFragment.setSections(presenter.provideFenceChoices())
        }
    }

    override fun setItems(items: List<FenceItem>) {
        adapter.setItems(items)
    }

    override fun notifyItemInserted(position: Int) {
        adapter.notifyItemInserted(position)
    }

    override fun notifyItemChanged(position: Int, payload: Any) {
        adapter.notifyItemChanged(position, payload)
    }

    override fun showPlaylist(playlist: Playlist) {
        adapter.setPlaylist(playlist)
    }

    override fun displayFenceChoice() {
        val picker = PickerDialogFragment.newInstance()
        picker.registerCallback(presenter)
        picker.setSections(presenter.provideFenceChoices())
        picker.show(fragmentManager, PickerDialogFragment.TAG)
    }

    override fun pickAPlaylist() {
        val intent = Intent(this.context, PlaylistsActivity::class.java)
        startActivityForResult(intent, PLAYLIST_PICKER_REQUEST_CODE)
    }

    override fun checkPermissionsAndShowLocationPicker(name: String, item: GridBottomSheetItem) {
        savedPlaceName = name
        savedGridBottomSheetItem = item
        checkLocationPermissionAndShowPicker()
    }

    override fun setRule(rule: Rule) {
        binding.rule = rule
    }

    override fun getPendingIntentFor(playlist: Playlist): PendingIntent {
        val intent = Intent(this.context, ThatService::class.java)
        return PendingIntent.getService(this.context.applicationContext,
                                        0,
                                        intent,
                                        0)
    }

    override fun finishActivity() {
        activity.finish()
    }

    override fun showSnack(errors: EnumSet<RuleErrors>) {

        var message = ""
        if (errors.contains(RuleErrors.NO_TITLE)) {
            message = getString(R.string.missing_title)
        } else if (errors.contains(RuleErrors.NO_CONDITION)) {
            message = getString(R.string.missing_condition)
        } else if (errors.contains(RuleErrors.NO_PLAYLIST)) {
            message = getString(R.string.missing_playlist)
        } else if (errors.contains(RuleErrors.SAVE_ERROR)) {
            message = getString(R.string.rule_save_error)
        }

        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PLAYLIST_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            val playlist = data!!.getParcelableExtra<Playlist>(PlaylistsContract.EXTRA_PLAYLIST)
            presenter.onPlaylistPicked(playlist)
        } else if (requestCode == LOCATION_PICKER_REQUEST_CODE
                && resultCode == RESULT_OK
                && !isEmpty(savedPlaceName)) {
            val place = PlacePicker.getPlace(context, data!!)
            val latLng = place.latLng
            presenter.onPlacePicked(savedPlaceName!!, savedGridBottomSheetItem!!, latLng)
        } else if (requestCode == PLAY_SERVICES_REQUEST_CODE
                && resultCode == RESULT_OK
                && !isEmpty(savedPlaceName)) {
            showLocationPicker()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE
                && grantResults.size == 1
                && grantResults[0] == PERMISSION_GRANTED) {
            showLocationPicker()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.edit_rule_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return presenter.onMenuItemClick(item!!.itemId)
    }


    private fun checkLocationPermissionAndShowPicker() {
        val permissionResult = checkSelfPermission(context,
                                                   ACCESS_FINE_LOCATION)
        val permissionGranted = permissionResult == PERMISSION_GRANTED
        if (permissionGranted) showLocationPicker()
        else {
            logD(TAG) { "getLocationPermission: permissionNeeded" }
            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
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

    companion object {

        private val TAG = RuleDetailsFragment::class.java.simpleName

        private val PLAYLIST_PICKER_REQUEST_CODE = 9001
        private val LOCATION_PICKER_REQUEST_CODE = 9002
        private val LOCATION_PERMISSION_REQUEST_CODE = 9003
        private val PLAY_SERVICES_REQUEST_CODE = 9004

        private val FINE_LOCATION_ARRAY = arrayOf(ACCESS_FINE_LOCATION)


        fun newInstance(): RuleDetailsFragment {
            return RuleDetailsFragment()
        }
    }


}
