package io.agora.rtckit.core.listener

import android.view.SurfaceView

interface IRtcServiceListener {

    /**设备,网络等状态*/
    fun onStatus()

    /**音频状态*/
    fun onAudioStatus()

    /**用户进入rtc 频道*/
    fun onUserJoinChannel(channel: String, uid: String, view: SurfaceView?)

    /**错误回调*/
    fun onError()
}