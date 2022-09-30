package io.agora.rtckit.middle

import io.agora.rtckit.open.IRtcValueCallback
import io.agora.rtckit.open.config.RtcChannelConfig
import io.agora.rtckit.open.event.*

/**
 * @author create by zhangwei03
 *
 * rtc 中间层服务监听
 */
interface IRtcMiddleService {

    fun joinChannel(config: RtcChannelConfig)

    fun leaveChannel()

    /**处理音频事件*/
    fun onAudioEvent(audioEvent: RtcAudioEvent)

    /**处理最佳音效事件*/
    fun onSoundEffectEvent(soundEffect: RtcSoundEffectEvent)

    /**处理AI 降噪事件*/
    fun onDeNoiseEvent(deNoiseEvent: RtcDeNoiseEvent,callback: IRtcValueCallback<Boolean>?)

    /**处理空间音频事件*/
    fun onSpatialAudioEvent(spatialAudioEvent: RtcSpatialAudioEvent)

    fun destroy()
}