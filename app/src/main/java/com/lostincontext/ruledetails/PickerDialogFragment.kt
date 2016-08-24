package com.lostincontext.ruledetails


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lostincontext.R
import com.lostincontext.commons.list.Section
import com.lostincontext.ruledetails.pick.GridBottomSheetItem
import com.lostincontext.databinding.RuleDetailsBottomSheetBinding
import com.lostincontext.ruledetails.pick.PickerDialogCallback
import com.lostincontext.ruledetails.pick.RulePickAdapter


// todo handle rotations
class PickerDialogFragment : BottomSheetDialogFragment(), PickerDialogCallback {


    private val adapter = RulePickAdapter(this)

    private var callback: PickerDialogCallback? = null


    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<RuleDetailsBottomSheetBinding>(inflater!!,
                                                                             R.layout.rule_details_bottom_sheet,
                                                                             container,
                                                                             false)

        val recyclerView = binding.recyclerView
        val span = 3
        val layoutManager = GridLayoutManager(context, span)
        recyclerView.layoutManager = layoutManager

        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                when (adapter.getItemViewType(position)) {
                    R.id.view_type_section_header -> return span
                    R.id.view_type_grid_bottom_sheet_item -> return 1
                }
                throw RuntimeException("unhandled view type")
            }
        }


        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(false)

        return binding.root
    }

    fun registerCallback(callback: PickerDialogCallback) {
        this.callback = callback
    }

    fun setSections(sections: List<Section<*>>) {
        adapter.setSections(sections)
    }

    override fun onGridBottomSheetItemClick(item: GridBottomSheetItem) {
        dismiss()
        callback!!.onGridBottomSheetItemClick(item)
    }

    companion object {

        val TAG = "PickerDialogFragment"

        fun newInstance(): PickerDialogFragment {
            return PickerDialogFragment()
        }
    }

}
