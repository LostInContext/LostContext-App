package com.lostincontext.data.user.repo

import com.lostincontext.data.user.UsersData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface DeezerUserSearchEndPoint {

    @GET("search/user")
    fun searchUser(@Query("q") query: String): Call<UsersData>

}
