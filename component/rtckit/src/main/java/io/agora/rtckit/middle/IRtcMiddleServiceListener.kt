package io.agora.rtckit.middle

import io.agora.rtckit.open.status.*

/**
 * @author create by zhangwei03
 *
 * rtc 中间层服务监听
 */
interface IRtcMiddleServiceListener {

    /**网络等状态，自己*/
    fun onNetworkStatus(netWorkStatus: RtcNetWorkStatus)

    /**音频状态：本地静音，远程静音等*/
    fun onAudioStatus(audioChangeStatus: RtcAudioChangeStatus)

    /**用户进入rtc 房间*/
    fun onUserJoined(userId: String)

    /**频道状态，用户开始加入频道、成功加入频道，用户退出等*/
    fun onChannelStatus(userStatus: RtcChannelStatus)

    /**错误回调*/
    fun onError(rtcErrorStatus: RtcErrorStatus)

    /**用户音量提示回调*/
    fun onAudioVolumeIndication(volumeIndicationStatus: RtcAudioVolumeIndicationStatus)
}