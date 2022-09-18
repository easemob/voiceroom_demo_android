package io.agora.secnceui.bean

/**
 * @author create by zhangwei03
 *
 * 排行榜用户
 */
class RoomRankUserBean constructor(
    var username: String = "",
    var userAvatar: String = "",
    var amount: Int = 0 //打赏数量
) : BaseRoomBean