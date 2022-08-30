package io.agora.rtckit.core.pipe

import android.view.SurfaceView

interface IRtcPipeListener {

    /**设备,网络等状态*/
    fun onStatus()

    /**音频状态*/
    fun onAudioStatus()

    /**用户进入rtc 房间*/
    fun onUserJoinChannel(channel: String, userId: String, view: SurfaceView?)

    /**错误回调*/
    fun onError()
}