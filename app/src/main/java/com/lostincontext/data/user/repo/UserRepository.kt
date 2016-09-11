package com.lostincontext.data.user.repo

import com.lostincontext.data.user.UserData
import com.lostincontext.utils.enqueue
import retrofit2.Response
import javax.inject.Inject


class UserRepository
@Inject constructor(public val searchEndPoint: DeezerUserSearchEndPoint) {


    inline fun queryUsers(query: String,
                          crossinline success: (response: Response<UserData>) -> Unit,
                          crossinline failure: (t: Throwable) -> Unit) {
        searchEndPoint.searchUser(query).enqueue(success,
                                                 failure)


    }
}