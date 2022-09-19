package io.agora.rtckit.internal

import io.agora.rtckit.open.status.RtcAudioChangeStatus
import io.agora.rtckit.open.status.RtcAudioVolumeIndicationStatus
import io.agora.rtckit.open.status.RtcChannelStatus
import io.agora.rtckit.open.status.RtcNetWorkStatus

/**
 * @author create by zhangwei03
 *
 * rtc 监听
 */
internal interface IRtcClientListener {
    //开始加入房间
    fun onJoinChannelStart(channel: String, userId: Int)

    //加入房间成功
    fun onJoinChannelSuccess(channel: String, userId: Int)

    //加入房间成功
    fun onJoinChannelError(channel: String, code: Int)

    //离开房间
    fun onUserLeave(userId: Int)

    // 用户加入
    fun onUserJoined(userId: Int)

    fun onAudioChangeStatus(audioStatus: RtcAudioChangeStatus)

    fun onNetWorkStatus(netWorkStatus: RtcNetWorkStatus)

    fun onConnectionStateChanged(state: Int, reason: Int)

    fun onAudioVolumeIndication(volumeIndicationStatus: RtcAudioVolumeIndicationStatus)

    fun onError(code: Int, msg: String)
}