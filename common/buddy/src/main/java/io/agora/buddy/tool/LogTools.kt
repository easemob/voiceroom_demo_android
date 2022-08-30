package io.agora.buddy.tool

import android.util.Log

fun String.logD(tag: String = "Agora_Buddy") {
    Log.d(tag, this)
}

fun String.logW(tag: String = "Agora_Buddy") {
    Log.w(tag, this)
}

fun String.logE(tag: String = "Agora_Buddy") {
    Log.e(tag, this)
}