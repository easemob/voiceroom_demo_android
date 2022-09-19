package io.agora.rtckit.internal

import io.agora.rtckit.middle.IRtcMiddleServiceListener
import io.agora.rtckit.open.status.*


/**
 * @author create by zhangwei03
 */
internal object RtcServiceHelper {
    fun createListener(middleListener: IRtcMiddleServiceListener): IRtcClientListener {
        return object : IRtcClientListener {

            override fun onJoinChannelStart(channel: String, userId: Int) {
                middleListener.onChannelStatus(RtcChannelStatus.Start(channel, userId))
            }

            override fun onJoinChannelSuccess(channel: String, userId: Int) {
                middleListener.onChannelStatus(RtcChannelStatus.JoinSuccess(channel, userId))
            }

            override fun onJoinChannelError(channel: String, code: Int) {
                middleListener.onChannelStatus(RtcChannelStatus.JoinError(channel, code))
            }

            override fun onUserLeave(userId: Int) {
                middleListener.onChannelStatus(RtcChannelStatus.Leave(userId))
            }


            override fun onUserJoined(userId: Int) {
                middleListener.onUserJoined(userId)
            }

            override fun onError(code: Int, msg: String) {
                middleListener.onError(RtcErrorStatus(code, msg))
            }

            override fun onAudioChangeStatus(audioStatus: RtcAudioChangeStatus) {
                middleListener.onAudioStatus(audioStatus)
            }

            override fun onNetWorkStatus(netWorkStatus: RtcNetWorkStatus) {
                middleListener.onNetworkStatus(netWorkStatus)
            }

            override fun onConnectionStateChanged(state: Int, reason: Int) {
                // nothing
            }

            override fun onAudioVolumeIndication(volumeIndicationStatus: RtcAudioVolumeIndicationStatus) {
                middleListener.onAudioVolumeIndication(volumeIndicationStatus)
            }
        }
    }


}