package io.agora.rtckit.open.status

/**
 * @author create by zhangwei03
 *
 * 频道状态改变
 */
sealed class RtcChannelStatus constructor(
    var channel: String = "",
    var userId: Int
) {

    /**开始加入房间*/
    class Start(channel: String, userId: Int) : RtcChannelStatus(channel, userId)

    /**加入房间成功*/
    class Success(channel: String, userId: Int) : RtcChannelStatus(channel, userId)

    /**离开房间*/
    class Leave(userId: Int) : RtcChannelStatus("", userId)
}
