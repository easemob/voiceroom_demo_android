package io.agora.rtckit.internal.base

import io.agora.rtckit.open.config.RtcChannelConfig

/**
 * @author create by zhangwei03
 *
 * 频道管理引擎
 */
internal abstract class RtcBaseChannelEngine<T> : RtcBaseEngine<T>() {

    abstract fun refreshChannel()

    abstract fun leaveChannel()

    abstract fun joinChannel(channelConfig: RtcChannelConfig): Boolean

    abstract fun switchRole(broadcast: Boolean)
}