package com.lostincontext.users

import com.lostincontext.commons.BasePresenter
import com.lostincontext.commons.BaseView
import com.lostincontext.commons.list.EmptyListCallback
import com.lostincontext.commons.list.StatefulAdapter
import com.lostincontext.data.user.User


object UsersContract {


    interface View : BaseView<Presenter> {

        fun setUsers(users: List<User>)

        fun setContentState(state: StatefulAdapter.ContentState)
        fun  openPlaylistsScreen(user: User)

    }

    interface Presenter : BasePresenter,
                          EmptyListCallback,
                          UserViewHolder.Callback {


        fun onUserSearch(query: CharSequence)


    }
}
