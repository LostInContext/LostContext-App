package com.lostincontext.data.user

import com.squareup.moshi.Json


data class UsersData(@Json(name = "data") val users: List<User>,
                     val total: Int,
                     val next: String)
