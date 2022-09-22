package io.agora.chatroom.controller

import android.content.Context
import io.agora.chatroom.BuildConfig
import io.agora.config.ConfigConstants
import io.agora.rtckit.open.IRtcKitListener
import io.agora.rtckit.open.IRtcValueCallback
import io.agora.rtckit.open.RtcKitManager
import io.agora.rtckit.open.config.RtcChannelConfig
import io.agora.rtckit.open.config.RtcInitConfig
import io.agora.rtckit.open.event.RtcDeNoiseEvent
import io.agora.rtckit.open.event.RtcSoundEffectEvent
import io.agora.rtckit.open.status.*
import io.agora.secnceui.annotation.AINSModeType
import io.agora.secnceui.bean.AINSModeBean
import io.agora.secnceui.bean.AINSSoundsBean
import tools.ValueCallBack

/**
 * @author create by zhangwei03
 */
class RtcRoomController : IRtcKitListener {

    companion object {

        private const val TAG = "RtcRoomController"

        @JvmStatic
        fun get() = InstanceHelper.sSingle
    }

    object InstanceHelper {
        val sSingle = RtcRoomController()
    }

    private val rtcChannelConfig by lazy {
        RtcChannelConfig(BuildConfig.agora_app_token)
    }

    private var rtcManger: RtcKitManager? = null

    /**降噪*/
    var anisMode = AINSModeType.Medium

    /**是否开启机器人*/
    var isUseBot: Boolean = false

    /**机器人音量*/
    var botVolume: Int = 50

    /**音效*/
    var soundEffect = ConfigConstants.Social_Chat

    /**加入rtc频道*/
    fun joinChannel(
        context: Context,
        roomId: String,
        userId: Int,
        broadcaster: Boolean = false,
        joinCallback: ValueCallBack<Boolean>
    ) {
        rtcManger = RtcKitManager.initRTC(context, RtcInitConfig(BuildConfig.agora_app_id), this)
        rtcChannelConfig.roomId = roomId
        rtcChannelConfig.userId = userId
        rtcChannelConfig.broadcaster = broadcaster
        rtcManger?.joinChannel(rtcChannelConfig, object : IRtcValueCallback<Boolean> {
            override fun onSuccess(value: Boolean) {
                // 默认开启降噪
                val event = when (anisMode) {
                    AINSModeType.Off -> RtcDeNoiseEvent.CloseEvent()
                    AINSModeType.High -> RtcDeNoiseEvent.HeightEvent()
                    else -> RtcDeNoiseEvent.MediumEvent()
                }
                rtcManger?.operateDeNoise(event)
                joinCallback.onSuccess(value)
            }
        })
    }

    fun deNoise(anisModeBean: AINSModeBean) {
        val event = when (anisModeBean.anisMode) {
            AINSModeType.Off -> RtcDeNoiseEvent.CloseEvent()
            AINSModeType.High -> RtcDeNoiseEvent.HeightEvent()
            else -> RtcDeNoiseEvent.MediumEvent()
        }
        rtcManger?.operateDeNoise(event)
    }

    fun playEffect(anisSoundsBean: AINSSoundsBean) {
        // test
        rtcManger?.operateSoundEffect(
            RtcSoundEffectEvent.PlayEffectEvent(
                1,
                "https://webdemo.agora.io/ding.mp3",
                1,
                true
            )
        )
    }

    fun destroy() {
        rtcManger?.leaveChannel()
        rtcManger?.destroy()
    }

    override fun onConnectionStateChanged(state: Int, reason: Int) {

    }

    override fun onNetworkStatus(netWorkStatus: RtcNetWorkStatus) {

    }

    override fun onAudioStatus(audioChangeStatus: RtcAudioChangeStatus) {
    }

    override fun onUserJoin(userId: Int) {
    }


    override fun onLeaveChannel(userId: Int) {

    }

    override fun onAudioEffectFinished(soundId: Int) {

    }

    override fun onError(rtcErrorStatus: RtcErrorStatus) {
    }

    override fun onAudioVolumeIndication(volumeIndicationStatus: RtcAudioVolumeIndicationStatus) {
    }
}