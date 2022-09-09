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
    fun onJoinChannelStart(channel: String, userId: String)

    //加入房间成功
    fun onJoinChannelSuccess(channel: String, userId: String)

    //离开房间
    fun onUserLeave(userId: String)

    // 用户加入
    fun onUserJoined(userId: String)

    /**
     * 频道状态，开始加入、加入成功，离开频道等
     */
    fun onChannelStatus(channelStatus: RtcChannelStatus)

    fun onAudioChangeStatus(audioStatus: RtcAudioChangeStatus)

    fun onNetWorkStatus(netWorkStatus: RtcNetWorkStatus)

    fun onConnectionStateChanged(state: Int, reason: Int)

    fun onAudioVolumeIndication(volumeIndicationStatus: RtcAudioVolumeIndicationStatus)

    fun onError(code: Int, msg: String)
}