package io.agora.rtckit.internal.impl

import io.agora.rtc2.IRtcEngineEventHandler
import io.agora.rtc2.RtcEngineEx
import io.agora.rtckit.internal.base.RtcBaseSoundEffectEngine

/**
 * @author create by zhangwei03
 *
 * agora 音效管理
 */
internal class AgoraRtcSoundEffectEngine : RtcBaseSoundEffectEngine<RtcEngineEx>() {
    override fun playEffect(
        soundId: Int, filePath: String, loopCount: Int, publish: Boolean, soundSpeakerType: Int
    ): Boolean {
        val result = engine?.playEffect(soundId, filePath, loopCount, 1.0, 0.0, 100.0, true)
        return (result == IRtcEngineEventHandler.ErrorCode.ERR_OK).also {
            if (it) {
                listener?.onAudioEffectFinished(soundId, false, soundSpeakerType)
            }
        }
    }

    override fun stopEffect(soundId: Int): Boolean {
        val result = engine?.stopEffect(soundId)
        return result == IRtcEngineEventHandler.ErrorCode.ERR_OK
    }

    override fun pauseEffect(soundId: Int): Boolean {
        val result = engine?.pauseEffect(soundId)
        return (result == IRtcEngineEventHandler.ErrorCode.ERR_OK)
    }

    override fun resumeEffect(soundId: Int): Boolean {
        val result = engine?.resumeEffect(soundId)
        return result == IRtcEngineEventHandler.ErrorCode.ERR_OK
    }

    override fun stopAllEffect(): Boolean {
        val result = engine?.stopAllEffects()
        return result == IRtcEngineEventHandler.ErrorCode.ERR_OK
    }
}