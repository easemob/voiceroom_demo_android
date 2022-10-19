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

    override fun joinChannel(config: RtcChannelConfig) {
        rtcClient.joinChannel(config)
    }

    override fun leaveChannel() {
        rtcClient.leaveChannel()
    }

    override fun switchRole(broadcaster: Boolean) {
        rtcClient.switchRole(broadcaster)
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
        rtcClient.getSoundEffectEngine()?.apply {
            when (soundEffect) {
                is RtcSoundEffectEvent.PlayEffectEvent -> playEffect(
                    soundEffect.soundId,
                    soundEffect.filePath,
                    soundEffect.loopback,
                    soundEffect.cycle,
                    soundEffect.soundSpeaker
                )
                is RtcSoundEffectEvent.StopEffectEvent -> stopEffect(soundEffect.soundId)
                is RtcSoundEffectEvent.PauseEffectEvent -> pauseEffect(soundEffect.soundId)
                is RtcSoundEffectEvent.ResumeEffectEvent -> resumeEffect(soundEffect.soundId)
                is RtcSoundEffectEvent.StopAllEffectEvent -> stopAllEffect()
            }
        }
    }

    override fun onDeNoiseEvent(deNoiseEvent: RtcDeNoiseEvent, callback: IRtcValueCallback<Boolean>?) {
        rtcClient.getDeNoiseEngine()?.apply {
            val result = when (deNoiseEvent) {
                is RtcDeNoiseEvent.CloseEvent -> closeDeNoise()
                is RtcDeNoiseEvent.MediumEvent -> openMediumDeNoise()
                is RtcDeNoiseEvent.HighEvent -> openHeightDeNoise()
            }
            callback?.onSuccess(result)
        }
    }

    override fun onSpatialAudioEvent(spatialAudioEvent: RtcSpatialAudioEvent) {
    }

    override fun destroy() {
        rtcClient.destroy()
    }
}