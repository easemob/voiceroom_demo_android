package io.agora.rtckit.open

import io.agora.rtckit.open.status.*

/**
 * @author create by zhangwei03
 */
open class RtcKitListenerImpl : IRtcKitListener {

    override fun onUserJoin(userId: Int) {
    }

    override fun onLeaveChannel(userId: Int) {
    }

    override fun onConnectionStateChanged(state: Int, reason: Int) {

    }

    override fun onNetworkStatus(netWorkStatus: RtcNetWorkStatus) {
    }

    override fun onAudioStatus(audioChangeStatus: RtcAudioChangeStatus) {
    }

    override fun onError(rtcErrorStatus: RtcErrorStatus) {
    }

    override fun onAudioVolumeIndication(volumeIndicationStatus: RtcAudioVolumeIndicationStatus) {
    }
}