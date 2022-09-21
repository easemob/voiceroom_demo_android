package io.agora.rtckit.open.event

/**
 * @author create by zhangwei03
 *
 * 音效操作事件
 */
sealed class RtcSoundEffectEvent {
    class PlayEffectEvent(val soundId: Int, val filePath: String, val loopCount: Int, val publish: Boolean) :
        RtcSoundEffectEvent()

    class StopEffectEvent(val soundId: Int): RtcSoundEffectEvent()
}
