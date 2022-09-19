package io.agora.rtckit.open

import android.content.Context
import io.agora.rtckit.internal.IRtcClientListener
import io.agora.rtckit.open.config.RtcInitConfig
import io.agora.rtckit.open.event.*
import io.agora.rtckit.open.status.*
import io.agora.rtckit.middle.RtcMiddleServiceImpl
import io.agora.rtckit.middle.IRtcMiddleService
import io.agora.rtckit.open.config.RtcChannelConfig

/**
 * @author create by zhangwei03
 *
 */
class RtcKitManager {

    private lateinit var initConfig: RtcInitConfig
    private var middleService: IRtcMiddleService? = null

    companion object {

        fun initRTC(context: Context, initConfig: RtcInitConfig, rtcKitListener: IRtcKitListener): RtcKitManager {
            val rtcKitManager = RtcKitManager().apply {
                this.initConfig = initConfig
                this.middleService = RtcMiddleServiceImpl(context, initConfig, object : IRtcClientListener {

                    override fun onConnectionStateChanged(state: Int, reason: Int) {
                        TODO("Not yet implemented")
                    }

                    override fun onUserJoined(userId: Int, joined: Boolean) {
                        if (joined) {
                            rtcKitListener.onUserJoin(userId)
                        } else {
                            rtcKitListener.onLeaveChannel(userId)
                        }
                    }

                    override fun onNetworkStatus(netWorkStatus: RtcNetWorkStatus) {
                        rtcKitListener.onNetworkStatus(netWorkStatus)
                    }

                    override fun onAudioStatus(audioChangeStatus: RtcAudioChangeStatus) {
                        rtcKitListener.onAudioStatus(audioChangeStatus)
                    }

                    override fun onError(rtcErrorStatus: RtcErrorStatus) {
                        rtcKitListener.onError(rtcErrorStatus)
                    }

                    override fun onAudioVolumeIndication(volumeIndicationStatus: RtcAudioVolumeIndicationStatus) {
                        rtcKitListener.onAudioVolumeIndication(volumeIndicationStatus)
                    }
                })
            }
            return rtcKitManager
        }
    }

    fun joinChannel(channelConfig: RtcChannelConfig, joinCallback: IRtcValueCallback<Boolean>) {
        middleService?.joinChannel(channelConfig, joinCallback)
    }

    fun leaveChannel() {
        middleService?.leaveChannel()
    }

    fun operateAudio(audioEvent: RtcAudioEvent) {
        middleService?.onAudioEvent(audioEvent)
    }

    fun operateSoundEffect(soundEffect: RtcSoundEffectEvent) {
        middleService?.onSoundEffectEvent(soundEffect)
    }

    fun operateDeNoise(rtcANISEvent: RtcDeNoiseEvent) {
        middleService?.onDeNoiseEvent(rtcANISEvent)
    }

    fun operateSpatialAudio(spatialAudioEvent: RtcSpatialAudioEvent) {
        middleService?.onSpatialAudioEvent(spatialAudioEvent)
    }

    fun destroy() {
        middleService?.destroy()
        middleService = null
    }
}