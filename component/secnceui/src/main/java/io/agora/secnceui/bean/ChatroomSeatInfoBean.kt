package io.agora.secnceui.bean

import io.agora.secnceui.annotation.*

data class SeatInfoBean constructor(
    var index: Int = 0,
    var userInfo: ChatroomUserInfoBean? = null,
    @WheatSeatType var wheatSeatType: Int = WheatSeatType.Idle,
    @WheatUserRole var userRole: Int = WheatUserRole.None,
    @WheatUserStatus var userStatus: Int = WheatUserStatus.None,
) : BaseChatroomBean

data class BotSeatInfoBean constructor(
    var blueBot: SeatInfoBean,
    var redBot: SeatInfoBean
) : BaseChatroomBean

data class SeatManagerBean constructor(
    val name: String,
    var enable: Boolean = true,
    @WheatSeatAction var seatActionType: Int = WheatSeatAction.Invite
) : BaseChatroomBean