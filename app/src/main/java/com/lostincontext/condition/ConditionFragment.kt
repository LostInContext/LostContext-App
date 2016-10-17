package com.lostincontext.condition

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.genius.groupie.GroupAdapter
import com.lostincontext.R
import com.lostincontext.application.LostApplication
import com.lostincontext.awareness.AwarenessModule
import com.lostincontext.commons.BaseActivity
import com.lostincontext.databinding.ConditionScreenFragmentBinding
import com.lostincontext.ruledetails.ConditionPresenterModule
import com.lostincontext.ruledetails.PickerDialogFragment
import javax.inject.Inject


class ConditionFragment : Fragment(), ConditionContract.View {


    @Inject lateinit internal var presenter: ConditionPresenter

    private lateinit var binding: ConditionScreenFragmentBinding

    private lateinit var adapter: GroupAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create the presenter
        DaggerConditionComponent.builder()
                .conditionPresenterModule(ConditionPresenterModule(this, savedInstanceState))
                .applicationComponent((activity.application as LostApplication).appComponent)
                .awarenessModule(AwarenessModule(activity as BaseActivity))
                .build()
                .inject(this)

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
        adapter = GroupAdapter(View.OnClickListener { })

        binding.plusButton.callback = presenter
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)

        val activity: AppCompatActivity = activity as AppCompatActivity
        activity.supportActionBar?.title = resources.getString(R.string.condition_title,
                                                               34) // todo condition number

        return binding.root
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


    companion object {

        fun newInstance(): ConditionFragment {
            return ConditionFragment()
        }
    }
}

