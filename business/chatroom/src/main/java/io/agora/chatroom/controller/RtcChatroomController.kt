package io.agora.chatroom.controller

import io.agora.rtckit.open.IRtcKitListener
import io.agora.rtckit.open.RtcKitManager
import io.agora.rtckit.open.status.*

/**
 * @author create by zhangwei03
 */
class RtcChatroomController : IRtcKitListener {
    private lateinit var rtcManger: RtcKitManager

    override fun onNetworkStatus(netWorkStatus: RtcNetWorkStatus) {

    }

    override fun onAudioStatus(audioChangeStatus: RtcAudioChangeStatus) {
    }

    override fun onUserJoin(userId: String) {
    }

    override fun onChannelStatus(userStatus: RtcChannelStatus) {
    }

    override fun onError(rtcErrorStatus: RtcErrorStatus) {
    }

    override fun onAudioVolumeIndication(volumeIndicationStatus: RtcAudioVolumeIndicationStatus) {
    }
}