package io.agora.rtckit.internal.impl

import io.agora.rtc2.RtcEngineEx
import io.agora.rtckit.internal.base.RtcBaseDeNoiseEngine

/**
 * @author create by zhangwei03
 *
 * AI 降噪管理
 */
internal class AgoraRtcDeNoiseEngine : RtcBaseDeNoiseEngine<RtcEngineEx>() {
    override fun startAudioMixing(
        filePath: String,
        loopBack: Boolean,
        replace: Boolean,
        loopCount: Int,
        startPosition: Int
    ): Boolean {
        return true
    }

    override fun stopAudioMixing(): Boolean {
        return true
    }

    override fun pauseAudioMixing(): Boolean {
        return true
    }

    override fun resumeAudioMixing(): Boolean {
        return true
    }

    override fun setAudioMixingPosition(position: Int): Boolean {
        return true
    }
}