package io.agora.rtckit.internal

import io.agora.rtckit.middle.IRtcMiddleServiceListener
import io.agora.rtckit.open.status.*


/**
 * @author create by zhangwei03
 */
internal object RtcServiceHelper {
    fun createListener(middleListener: IRtcMiddleServiceListener): IRtcClientListener {
        return object : IRtcClientListener {

            override fun onJoinChannelStart(channel: String, userId: String) {
                middleListener.onChannelStatus(RtcChannelStatus.Start(channel, userId))
            }

            override fun onJoinChannelSuccess(channel: String, userId: String) {
                middleListener.onChannelStatus(RtcChannelStatus.Success(channel, userId))
            }

            override fun onUserLeave(userId: String) {
                middleListener.onChannelStatus(RtcChannelStatus.Leave(userId))
            }


            override fun onUserJoined(userId: String) {
                middleListener.onUserJoined(userId)
            }

            override fun onChannelStatus(channelStatus: RtcChannelStatus) {
                middleListener.onChannelStatus(channelStatus)
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