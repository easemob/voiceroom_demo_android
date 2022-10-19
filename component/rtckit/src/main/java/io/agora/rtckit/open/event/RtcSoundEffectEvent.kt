package io.agora.rtckit.open.event

import io.agora.config.ConfigConstants

/**
 * @author create by zhangwei03
 *
 * 音效操作事件
 */
sealed class RtcSoundEffectEvent {
    class PlayEffectEvent constructor(
        val soundId: Int,
        val filePath: String,
        val loopback: Boolean,
        val cycle: Int,
        val soundSpeaker: Int = ConfigConstants.BotSpeaker.BotBlue
    ) :
        RtcSoundEffectEvent()

    class StopEffectEvent constructor(val soundId: Int) : RtcSoundEffectEvent()

    class PauseEffectEvent constructor(val soundId: Int) : RtcSoundEffectEvent()

    class ResumeEffectEvent constructor(val soundId: Int) : RtcSoundEffectEvent()

    class StopAllEffectEvent constructor() : RtcSoundEffectEvent()
}
