package com.lostincontext.data.user.repo

import com.lostincontext.data.user.UsersData
import com.lostincontext.utils.enqueue
import javax.inject.Inject


class UserRepository
@Inject constructor(val searchEndPoint: DeezerUserSearchEndPoint) {


    inline fun queryUsers(query: String,
                          crossinline success: (usersData: UsersData) -> Unit,
                          crossinline failure: (t: Int) -> Unit) {
        searchEndPoint.searchUser(query).enqueue(success,
                                                 failure)


    }
}