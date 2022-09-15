package io.agora.rtckit.middle

import android.content.Context
import io.agora.rtckit.open.config.RtcInitConfig
import io.agora.rtckit.internal.RtcBaseClientEx
import io.agora.rtckit.open.event.*
import io.agora.rtckit.internal.AgoraRtcClientEx

/**
 * @author create by zhangwei03
 *
 */
class RtcMiddleServiceImpl : IRtcMiddleService {

    private var rtcMiddleListener: IRtcMiddleServiceListener? = null

    private val rtcClient: RtcBaseClientEx<*> by lazy {
        AgoraRtcClientEx()
    }

    /**机器人小蓝*/
    private val rtcBlueBotClient:RtcBaseClientEx<*> by lazy {
        AgoraRtcClientEx()
    }

    /**机器人小红*/
    private val rtcRedBotClient:RtcBaseClientEx<*> by lazy {
        AgoraRtcClientEx()
    }

    override fun initMain(context: Context, middleListener: IRtcMiddleServiceListener, config: RtcInitConfig) {
        this.rtcMiddleListener = middleListener
        rtcClient.createClient(context, config, middleListener)
    }

    override fun onChannelEvent(channelEvent: RtcChannelEvent) {
        rtcClient.getChannelEngine()?.apply {
            when (channelEvent) {
                is RtcChannelEvent.JoinChannel -> joinChannel(channelEvent.channelConfig)
                is RtcChannelEvent.LeaveChannel -> leaveChannel()
                is RtcChannelEvent.RefreshChannel -> refreshChannel()
                is RtcChannelEvent.SwitchRole -> switchRole(channelEvent.broadcast)
            }
        }
    }

    override fun onAudioEvent(audioEvent: RtcAudioEvent) {
        rtcClient.getAudioEngine()?.apply {
            when (audioEvent) {
                is RtcAudioEvent.AudioEnable -> enableLocalAudio(audioEvent.enabled)
                is RtcAudioEvent.AudioMuteLocal -> muteLocalAudio(audioEvent.mute)
                is RtcAudioEvent.AudioMuteRemote -> muteRemoteAudio(audioEvent.userId, audioEvent.mute)
                is RtcAudioEvent.AudioMuteAll -> muteRemoteAllAudio(audioEvent.mute)
            }
        }
    }

    override fun onSoundEffectEvent(soundEffect: RtcSoundEffectEvent) {
        // TODO:
    }

    override fun onDeNoiseEvent(rtcDeNoiseEvent: RtcDeNoiseEvent) {
        // TODO:
    }

    override fun onSpatialAudioEvent(spatialAudioEvent: RtcSpatialAudioEvent) {
        // TODO:
    }

    override fun destroy() {
        rtcMiddleListener = null
        rtcClient.destroy()
    }
}