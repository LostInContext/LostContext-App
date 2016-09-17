package com.lostincontext.utils

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


const val FAILURE_CODE = -1

inline fun <T> Call<T>.enqueue(crossinline success: (response: T) -> Unit,
                               crossinline failure: (error: Int) -> Unit) {
    enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {

            if (response.isSuccessful) {
                success(response.body())
            } else {
                logE("Retrofit") { "unsuccessful call : code ${response.code()}" }
                failure(response.code())
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            logE("Retrofit") { t.message ?: "empty error" }
            failure(FAILURE_CODE)
        }

    })
}
