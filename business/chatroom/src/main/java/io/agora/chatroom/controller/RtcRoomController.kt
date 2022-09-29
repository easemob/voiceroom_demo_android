package io.agora.chatroom.controller

import android.content.Context
import io.agora.buddy.tool.ThreadManager
import io.agora.chatroom.BuildConfig
import io.agora.config.ConfigConstants
import io.agora.rtckit.open.IRtcKitListener
import io.agora.rtckit.open.RtcKitManager
import io.agora.rtckit.open.config.RtcChannelConfig
import io.agora.rtckit.open.config.RtcInitConfig
import io.agora.rtckit.open.event.RtcAudioEvent
import io.agora.rtckit.open.event.RtcDeNoiseEvent
import io.agora.rtckit.open.event.RtcSoundEffectEvent
import io.agora.rtckit.open.status.*
import io.agora.secnceui.bean.AINSModeBean
import io.agora.secnceui.bean.SoundAudioBean
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

    /**第一次启动机器，播放*/
    var firstActiveBot = true

    /**第一次切换ai 降噪*/
    var firstSwitchAnis = true

    /**降噪*/
    var anisMode = ConfigConstants.AINSMode.AINS_Medium

    /**是否开启机器人*/
    var isUseBot: Boolean = false
        set(value) {
            field = value
            if (!field) {
                stopAllEffect()
            }
        }

    /**机器人音量*/
    var botVolume: Int = 50

    /**音效*/
    var soundEffect = ConfigConstants.SoundSelection.Social_Chat

    private var micVolumeListener: RtcMicVolumeListener? = null

    fun setMicVolumeListener(micVolumeListener: RtcMicVolumeListener) {
        this.micVolumeListener = micVolumeListener
    }


    private var joinCallback:ValueCallBack<Boolean>?=null

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
        this.joinCallback =  joinCallback
        rtcManger?.joinChannel(rtcChannelConfig)
    }

    /**
     * 降噪控制
     */
    fun deNoise(anisModeBean: AINSModeBean) {
        val event = when (anisModeBean.anisMode) {
            ConfigConstants.AINSMode.AINS_Off -> RtcDeNoiseEvent.CloseEvent()
            ConfigConstants.AINSMode.AINS_High -> RtcDeNoiseEvent.HeightEvent()
            else -> RtcDeNoiseEvent.MediumEvent()
        }
        rtcManger?.operateDeNoise(event)
    }

    /**
     * 音效队列
     */
    private val soundAudioQueue: ArrayDeque<SoundAudioBean> = ArrayDeque()

    /**
     * 播放音效
     */
    fun playEffect(soundAudioList: List<SoundAudioBean>) {
        // 暂停其他音效播放
        rtcManger?.operateSoundEffect(
            RtcSoundEffectEvent.StopAllEffectEvent()
        )
        // 加入音效队列
        soundAudioQueue.clear()
        soundAudioQueue.addAll(soundAudioList)
        // 取队列第一个播放
        soundAudioQueue.removeFirstOrNull()?.let {
            rtcManger?.operateSoundEffect(
                RtcSoundEffectEvent.PlayEffectEvent(it.soundId, it.audioUrl, 0, true, it.speakerType)
            )
        }
    }

    /**
     * 播放音效
     */
    fun playEffect(soundId: Int, audioUrl: String, speakerType: Int) {
        rtcManger?.let {
            // 暂停其他音效播放
            it.operateSoundEffect(RtcSoundEffectEvent.StopAllEffectEvent())
            it.operateSoundEffect(RtcSoundEffectEvent.PlayEffectEvent(soundId, audioUrl, 0, true, speakerType))
        }

    }

    /**
     * 暂停所有音效播放
     */
    fun stopAllEffect() {
        soundAudioQueue.clear()
        rtcManger?.operateSoundEffect(RtcSoundEffectEvent.StopAllEffectEvent())
    }

    /**
     * 本地mute/unmute
     */
    fun enableLocalAudio(mute: Boolean) {
        rtcManger?.operateAudio(RtcAudioEvent.AudioMuteLocal(mute))
    }

    fun destroy() {
        // 退出房间恢复默认值
        firstActiveBot = true
        firstSwitchAnis = true
        anisMode = ConfigConstants.AINSMode.AINS_Medium
        isUseBot = false
        botVolume = 50
        soundEffect = ConfigConstants.SoundSelection.Social_Chat
        rtcManger?.leaveChannel()
        rtcManger?.destroy()
    }

    override fun onJoinChannelSuccess(channel: String, uid: Int, elapsed: Int) {
        // 默认开启降噪
        val event = when (anisMode) {
            ConfigConstants.AINSMode.AINS_Off -> RtcDeNoiseEvent.CloseEvent()
            ConfigConstants.AINSMode.AINS_High -> RtcDeNoiseEvent.HeightEvent()
            else -> RtcDeNoiseEvent.MediumEvent()
        }
        rtcManger?.operateDeNoise(event)
        joinCallback?.onSuccess(true)
    }

    override fun onLeaveChannel() {

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

    override fun onAudioEffectFinished(soundId: Int, finished: Boolean, speakerType: Int) {
        if (finished) {
            // 结束播放回调--->>播放下一个，取队列第一个播放
            ThreadManager.getInstance().runOnMainThread {
                micVolumeListener?.onBotVolume(speakerType, true)
            }
            soundAudioQueue.removeFirstOrNull()?.let {
                rtcManger?.operateSoundEffect(
                    RtcSoundEffectEvent.PlayEffectEvent(it.soundId, it.audioUrl, 0, true, it.speakerType)
                )
            }
        } else {
            // 开始播放回调--->>
            ThreadManager.getInstance().runOnMainThread {
                micVolumeListener?.onBotVolume(speakerType, false)
            }
        }
    }

    override fun onError(rtcErrorStatus: RtcErrorStatus) {
    }

    override fun onAudioVolumeIndication(volumeIndicationStatus: RtcAudioVolumeIndicationStatus) {
    }
}