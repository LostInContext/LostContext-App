package com.lostincontext.utils

import android.util.Log
import com.lostincontext.BuildConfig


inline fun logD(tag: String, scribe: () -> String) {
    if (BuildConfig.LOG) Log.d(tag, scribe())
}

inline fun logI(tag: String, scribe: () -> String) {
    if (BuildConfig.LOG) Log.i(tag, scribe())
}


inline fun logW(tag: String, scribe: () -> String) {
    if (BuildConfig.LOG) Log.w(tag, scribe())
}

inline fun logE(tag: String, scribe: () -> String) {
    if (BuildConfig.LOG) Log.e(tag, scribe())
}

inline fun logE(tag: String, e: Throwable, scribe: () -> String) {
    if (BuildConfig.LOG) Log.e(tag, scribe(), e)
}