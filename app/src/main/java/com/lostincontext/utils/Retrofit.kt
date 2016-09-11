package com.lostincontext.utils

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


inline fun <T> Call<T>.enqueue(crossinline success: (response: Response<T>) -> Unit,
                               crossinline failure: (t: Throwable) -> Unit) {
    enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            success(response)
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            failure(t)
        }

    })
}
