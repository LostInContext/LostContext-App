package com.lostincontext.users;


import android.os.Bundle
import com.lostincontext.commons.list.StatefulAdapter.ContentState.ERROR
import com.lostincontext.data.user.User
import com.lostincontext.data.user.repo.UserRepository
import com.lostincontext.utils.logD
import javax.inject.Inject

class UsersPresenter : UsersContract.Presenter {


    private val view: UsersContract.View
    private val usersRepository: UserRepository

    @Inject internal constructor(view: UsersContract.View,
                                 usersRepository: UserRepository,
                                 icicle: Bundle?) {
        this.view = view
        this.usersRepository = usersRepository

    }

    override fun start() {

    }


    override fun onItemClick(user: User) {

    }


    override fun onUserSearch(query: CharSequence) {
        logD(TAG) { "onUserSearch : $query" }
        usersRepository.queryUsers(query.toString(),
                                   {
                                       if (it.users != null) view.setUsers(it.users)
                                       else view.setUsers(emptyList())
                                   },
                                   {
                                       view.setContentState(ERROR)
                                   })
    }

    override fun onRefreshButtonClick() {

    }

    companion object {
        private val TAG: String = UsersPresenter::class.java.simpleName
    }

}

