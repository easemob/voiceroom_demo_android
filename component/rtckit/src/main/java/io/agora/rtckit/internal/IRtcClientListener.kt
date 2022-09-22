package io.agora.rtckit.internal

import io.agora.rtckit.open.status.RtcAudioChangeStatus
import io.agora.rtckit.open.status.RtcAudioVolumeIndicationStatus
import io.agora.rtckit.open.status.RtcErrorStatus
import io.agora.rtckit.open.status.RtcNetWorkStatus

/**
 * @author create by zhangwei03
 *
 * rtc 监听
 */
interface IRtcClientListener {

    /**网络情况*/
    fun onConnectionStateChanged(state: Int, reason: Int)

    /**用户进入/离开 rtc 房间*/
    fun onUserJoined(userId: Int, joined: Boolean)

    /**网络等状态，自己*/
    fun onNetworkStatus(netWorkStatus: RtcNetWorkStatus)

    /**音频状态：本地静音，远程静音等*/
    fun onAudioStatus(audioChangeStatus: RtcAudioChangeStatus)

    /**当音效文件播放结束后触发该回调*/
    fun onAudioEffectFinished(soundId: Int)

    /**错误回调*/
    fun onError(rtcErrorStatus: RtcErrorStatus)

    /**用户音量提示回调*/
    fun onAudioVolumeIndication(volumeIndicationStatus: RtcAudioVolumeIndicationStatus)
}