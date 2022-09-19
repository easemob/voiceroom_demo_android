package io.agora.rtckit.open

import io.agora.rtckit.open.status.*

/**
 * @author create by zhangwei03
 *
 * 管道监听
 */
interface IRtcKitListener {

    /**网络情况*/
    fun onConnectionStateChanged(state: Int, reason: Int)

    /**网络等状态*/
    fun onNetworkStatus(netWorkStatus: RtcNetWorkStatus)

    /**音频状态：本地静音，远程静音等*/
    fun onAudioStatus(audioChangeStatus: RtcAudioChangeStatus)

    /**用户进入rtc 房间*/
    fun onUserJoin(userId: Int)

    fun onLeaveChannel(userId: Int)

    /**错误回调*/
    fun onError(rtcErrorStatus: RtcErrorStatus)

    /**用户音量提示回调。*/
    fun onAudioVolumeIndication(volumeIndicationStatus: RtcAudioVolumeIndicationStatus)
}