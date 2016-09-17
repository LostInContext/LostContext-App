package com.lostincontext.users;


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lostincontext.R
import com.lostincontext.application.LostApplication
import com.lostincontext.commons.list.StatefulAdapter
import com.lostincontext.data.user.User
import com.lostincontext.databinding.UsersScreenFragmentBinding
import javax.inject.Inject

class UsersFragment : Fragment(), UsersContract.View {

    @Inject lateinit internal var presenter: UsersPresenter

    private lateinit var adapter: UsersAdapter
    private lateinit var binding: UsersScreenFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerUsersComponent.builder()
                .usersPresenterModule(UsersPresenterModule(this, savedInstanceState))
                .applicationComponent((activity.application as LostApplication).appComponent)
                .build()
                .inject(this)
    }


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate<UsersScreenFragmentBinding>(inflater,
                                                                      R.layout.users_screen_fragment,
                                                                      container,
                                                                      false)


        if (savedInstanceState != null) {
            val savedQuery = savedInstanceState.getString(KEY_SEARCH,
                                                          "")
            binding.userInputEditText.setText(savedQuery)
        }

        val recyclerView = binding.recyclerView

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        adapter = UsersAdapter(presenter, presenter)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)

        binding.presenter = presenter

        return binding.root
    }


    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(KEY_SEARCH,
                           binding.userInputEditText.text.toString())
        super.onSaveInstanceState(outState)
    }

    //region view implementation

    override fun setUsers(users: List<User>) = adapter.setUsers(users)

    override fun setContentState(state: StatefulAdapter.ContentState) {
        adapter.currentState = state
    }
    //endregion

    companion object {

        private const val KEY_SEARCH = "user_query"

        private val TAG = UsersFragment::class.java.simpleName

        fun newInstance(): UsersFragment {
            return UsersFragment()
        }
    }
}
