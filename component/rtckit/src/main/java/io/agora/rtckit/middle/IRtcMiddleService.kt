package io.agora.rtckit.middle

import android.content.Context
import io.agora.rtckit.open.config.RtcInitConfig
import io.agora.rtckit.open.event.*

/**
 * @author create by zhangwei03
 *
 * rtc 中间层服务监听
 */
interface IRtcMiddleService {

    fun initMain(context: Context, middleListener: IRtcMiddleServiceListener, config: RtcInitConfig)

    /**处理音频事件*/
    fun onAudioEvent(audioEvent: RtcAudioEvent)

    /**处理频道事件*/
    fun onChannelEvent(channelEvent: RtcChannelEvent)

    /**处理最佳音效事件*/
    fun onSoundEffectEvent(soundEffect: RtcSoundEffectEvent)

    /**处理AI 降噪事件*/
    fun onDeNoiseEvent(rtcDeNoiseEvent: RtcDeNoiseEvent)

    /**处理空间音频事件*/
    fun onSpatialAudioEvent(spatialAudioEvent: RtcSpatialAudioEvent)

    fun destroy()
}