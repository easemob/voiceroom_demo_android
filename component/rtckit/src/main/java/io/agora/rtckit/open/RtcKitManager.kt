package io.agora.rtckit.open

import android.app.Application
import io.agora.rtckit.open.config.RtcInitConfig
import io.agora.rtckit.open.event.*
import io.agora.rtckit.open.status.*
import io.agora.rtckit.middle.RtcMiddleServiceImpl
import io.agora.rtckit.middle.IRtcMiddleServiceListener
import io.agora.rtckit.middle.IRtcMiddleService

/**
 * @author create by zhangwei03
 *
 */
class RtcKitManager {

    private var middleService: IRtcMiddleService? = null
    lateinit var initConfig: RtcInitConfig
    var rtcManagerListener: IRtcKitListener? = null

    companion object {

        fun initRTC(
            appContext: Application,
            initConfig: RtcInitConfig,
            managerListener: IRtcKitListener
        ): RtcKitManager {
            return RtcKitManager().apply {
                this.rtcManagerListener = managerListener
                this.initConfig = initConfig
                create(appContext)
            }
        }
    }

    private fun create(appContext: Application) {
        middleService = RtcMiddleServiceImpl().apply {
            initMain(appContext, object : IRtcMiddleServiceListener {

                override fun onNetworkStatus(netWorkStatus: RtcNetWorkStatus) {
                    rtcManagerListener?.onNetworkStatus(netWorkStatus)
                }

                override fun onAudioStatus(audioChangeStatus: RtcAudioChangeStatus) {
                    rtcManagerListener?.onAudioStatus(audioChangeStatus)
                }

                override fun onUserJoined(userId: Int) {
                    rtcManagerListener?.onUserJoin(userId)
                }

                override fun onChannelStatus(userStatus: RtcChannelStatus) {
                    rtcManagerListener?.onChannelStatus(userStatus)
                }

                override fun onError(rtcErrorStatus: RtcErrorStatus) {
                    rtcManagerListener?.onError(rtcErrorStatus)
                }

                override fun onAudioVolumeIndication(volumeIndicationStatus: RtcAudioVolumeIndicationStatus) {
                    rtcManagerListener?.onAudioVolumeIndication(volumeIndicationStatus)
                }

            }, initConfig)
        }
    }

    fun operateAudio(audioEvent: RtcAudioEvent) {
        middleService?.onAudioEvent(audioEvent)
    }

    fun operateChannel(channelEvent: RtcChannelEvent) {
        middleService?.onChannelEvent(channelEvent)
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
        rtcManagerListener = null
        middleService?.destroy()
        middleService = null
    }
}