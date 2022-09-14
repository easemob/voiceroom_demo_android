package io.agora.secnceui.bean

import io.agora.secnceui.annotation.*

data class SeatInfoBean constructor(
    val index: Int = 0,
    val userId: String? = null,
    val name: String? = null,
    val avatar: String = "",
    @WheatSeatType val wheatSeatType: Int = WheatSeatType.Idle,
    @WheatUserRole val userRole: Int = WheatUserRole.None,
    @WheatUserStatus val userStatus: Int = WheatUserStatus.None,
) : BaseChatroomBean

data class BotSeatInfoBean constructor(
    val blueBot: SeatInfoBean,
    val redBot: SeatInfoBean
) : BaseChatroomBean

data class SeatManagerBean constructor(
    val name: String,
    var enable: Boolean = true,
    @WheatSeatAction var seatActionType: Int = WheatSeatAction.Invite
) : BaseChatroomBean