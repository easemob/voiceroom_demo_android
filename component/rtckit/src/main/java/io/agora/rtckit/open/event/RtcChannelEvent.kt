package io.agora.rtckit.open.event

import io.agora.rtckit.open.config.RtcChannelConfig

/**
 * @author create by zhangwei03
 *
 * 频道操作事件
 */
sealed class RtcChannelEvent {

    /**加入频道*/
    class JoinChannel(val channelConfig: RtcChannelConfig) : RtcChannelEvent()

    /**切换角色*/
    class SwitchRole(val broadcast: Boolean) : RtcChannelEvent()

    /**刷新频道*/
    object RefreshChannel : RtcChannelEvent()

    /**离开频道*/
    class LeaveChannel : RtcChannelEvent()
}