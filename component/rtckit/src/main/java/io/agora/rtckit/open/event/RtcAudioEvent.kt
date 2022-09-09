package io.agora.rtckit.open.event

/**
 * @author create by zhangwei03
 *
 * 音频操作事件
 */
sealed class RtcAudioEvent(enabled: Boolean) {

    /**音频启动/停止*/
    class AudioEnable(val enabled: Boolean) : RtcAudioEvent(enabled)

    /**本地音频静音/开启*/
    class AudioMuteLocal(val mute: Boolean) : RtcAudioEvent(mute)

    /**远程音频静音/开启*/
    class AudioMuteRemote(val userId: String, val mute: Boolean) : RtcAudioEvent(mute)

    /**所有音频静音/开启*/
    class AudioMuteAll(val userId: String, val mute: Boolean) : RtcAudioEvent(mute)
}