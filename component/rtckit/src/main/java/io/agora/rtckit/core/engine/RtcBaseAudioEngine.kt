package io.agora.rtckit.core.engine

import io.agora.rtckit.core.RtcChannelConfig

/**
 * 音频引擎
 */
abstract class RtcBaseAudioEngine<T> : RtcBaseEngine<T>() {

    /**创建rtc 房间*/
    abstract fun createChannel()

    /**加入rtc 房间*/
    abstract fun joinChannel(config: RtcChannelConfig): T?

    /**离开rtc 房间*/
    abstract fun leaveChannel()

    /**销毁rtc 引擎*/
    abstract fun destroy()

    /**开始audio*/
    abstract fun startAudio()

    /**停止audio*/
    abstract fun stopAudio()

    /**禁本地audio*/
    abstract fun muteLocalAudio(mute: Boolean)

    /**禁远程audio*/
    abstract fun muteRemoteAudio(mute: Boolean)
}