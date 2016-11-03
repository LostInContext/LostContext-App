package com.lostincontext.users;


import android.os.Bundle
import com.lostincontext.commons.list.StatefulAdapter.ContentState.ERROR
import com.lostincontext.data.user.User
import com.lostincontext.data.user.repo.UserRepository
import com.lostincontext.utils.logD
import javax.inject.Inject

class UsersPresenter @Inject internal constructor(private val view: UsersContract.View,
                                                  private val usersRepository: UserRepository,
                                                  icicle: Bundle?)
    : UsersContract.Presenter {


    private var query: CharSequence? = null

    init {
        icicle?.let { query = it.getCharSequence(KEY_QUERY) }
        logD(TAG) { "icicle : $icicle" }
    }

    override fun start() {
        logD(TAG) { "query : $query" }
        query?.let { onUserSearch(it) }
    }

    override fun saveState(outState: Bundle) = outState.putCharSequence(KEY_QUERY, query)


    override fun onItemClick(user: User) = view.openPlaylistsScreen(user)


    override fun onUserSearch(query: CharSequence) {
        logD(TAG) { "onUserSearch : $query" }
        this.query = query
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
        const private val KEY_QUERY = "queryUsersPresenter"
    }


}

