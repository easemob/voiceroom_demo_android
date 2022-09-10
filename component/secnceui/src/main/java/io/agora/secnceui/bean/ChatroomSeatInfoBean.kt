package io.agora.secnceui.bean

import io.agora.secnceui.annotation.*
import io.agora.secnceui.bean.enum.EnumAvatar
import io.agora.secnceui.bean.enum.EnumBot

data class SeatInfoBean constructor(
    val index: Int = 0,
    val userId: String? = null,
    val name: String? = null,
    val avatar: EnumAvatar = EnumAvatar.Man1,
    @WheatSeatType val wheatSeatType: Int = WheatSeatType.Idle,
    @WheatUserRole val userRole: Int = WheatUserRole.None,
    @WheatUserStatus val userStatus: Int = WheatUserStatus.None,
    val rot: EnumBot = EnumBot.AgoraBlue,
) : BaseChatroomBean

data class BotSeatInfoBean(
    val blueBot: SeatInfoBean,
    val redBot: SeatInfoBean
) : BaseChatroomBean

data class SeatManagerBean constructor(
    val name: String,
    var enable: Boolean = true,
    @WheatSeatAction var seatActionType: Int = WheatSeatAction.Invite
) : BaseChatroomBean