package io.agora.rtckit.internal.base

/**
 * @author create by zhangwei03
 *
 * AI 降噪管理引擎
 */
internal abstract class RtcBaseDeNoiseEngine<T> : RtcBaseEngine<T>() {

    /**
     *  @param filePath 文件路径
     *  @param loopBack  true 只有本地能听 false 远端和本地都能听到
     *  @param replace  true 只能听到音乐 false 音乐和麦克风都能被听到
     *  @param loopCount 循环次数
     *  @param startPosition 开始播放位置 毫秒
     */
    abstract fun startAudioMixing(
        filePath: String,
        loopBack: Boolean,
        replace: Boolean,
        loopCount: Int,
        startPosition: Int
    ): Boolean

    abstract fun stopAudioMixing(): Boolean

    abstract fun pauseAudioMixing(): Boolean

    abstract fun resumeAudioMixing(): Boolean

    abstract fun setAudioMixingPosition(position: Int): Boolean
}