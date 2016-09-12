package com.lostincontext.data.user.repo

import com.lostincontext.data.user.UsersData
import com.lostincontext.utils.enqueue
import retrofit2.Response
import javax.inject.Inject


class UserRepository
@Inject constructor(val searchEndPoint: DeezerUserSearchEndPoint) {


    inline fun queryUsers(query: String,
                          crossinline success: (response: Response<UsersData>) -> Unit,
                          crossinline failure: (t: Throwable) -> Unit) {
        searchEndPoint.searchUser(query).enqueue(success,
                                                 failure)


    }
}