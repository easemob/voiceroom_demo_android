package io.agora.rtckit.middle

import android.content.Context
import io.agora.rtckit.open.config.RtcInitConfig
import io.agora.rtckit.internal.RtcBaseClientEx
import io.agora.rtckit.open.event.*
import io.agora.rtckit.internal.AgoraRtcClientEx
import io.agora.rtckit.internal.IRtcClientListener
import io.agora.rtckit.open.IRtcValueCallback
import io.agora.rtckit.open.config.RtcChannelConfig

/**
 * @author create by zhangwei03
 *
 */
class RtcMiddleServiceImpl constructor(
    context: Context, config: RtcInitConfig, rtcClientListener: IRtcClientListener
) : IRtcMiddleService {

    private val rtcClient: RtcBaseClientEx<*> by lazy {
        AgoraRtcClientEx()
    }

    init {
        rtcClient.createClient(context, config, rtcClientListener)
    }

    override fun joinChannel(config: RtcChannelConfig, joinCallback: IRtcValueCallback<Boolean>) {
        rtcClient.joinChannel(config, joinCallback)
    }

    override fun leaveChannel() {
        rtcClient.leaveChannel()
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
    }

    override fun onDeNoiseEvent(rtcDeNoiseEvent: RtcDeNoiseEvent) {
    }

    override fun onSpatialAudioEvent(spatialAudioEvent: RtcSpatialAudioEvent) {
    }

    override fun destroy() {
        rtcClient.destroy()
    }
}