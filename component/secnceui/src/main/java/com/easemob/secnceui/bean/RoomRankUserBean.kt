package com.easemob.secnceui.bean

/**
 * @author create by zhangwei03
 *
 * 排行榜用户
 */
data class RoomRankUserBean constructor(
    var username: String = "",
    var userAvatar: String = "",
    var amount: Int = 0 //打赏数量
) : com.easemob.secnceui.bean.BaseRoomBean