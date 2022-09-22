package io.agora.rtckit.open.event

/**
 * @author create by zhangwei03
 *
 * 音频操作事件
 */
sealed class RtcAudioEvent {

    /**音频启动/停止*/
    class AudioEnable(val enabled: Boolean) : RtcAudioEvent()

    /**本地音频静音/开启*/
    class AudioMuteLocal(val mute: Boolean) : RtcAudioEvent()

    /**远程音频静音/开启*/
    class AudioMuteRemote(val userId: String, val mute: Boolean) : RtcAudioEvent()

    /**所有音频静音/开启*/
    class AudioMuteAll(val userId: String, val mute: Boolean) : RtcAudioEvent()
}