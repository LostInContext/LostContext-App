package com.lostincontext.data.user


import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson


class UserImageAdapter {

    @FromJson @UserImage fun fromJson(image: String): String {
        val beginning = image.indexOf(user) + user.length
        val end = image.indexOf("/", beginning) - 1
        val substring = image.substring(beginning..end)
        return substring
    }

    @ToJson fun toJson(@UserImage image: String): String {
        return "$user$image/"
    }

    companion object {
        const val user = "/user/"
    }
}