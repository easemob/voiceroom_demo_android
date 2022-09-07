package io.agora.secnceui.bean

import androidx.annotation.DrawableRes
import io.agora.secnceui.annotation.WheatSeatAction
import io.agora.secnceui.annotation.WheatSeatType
import io.agora.secnceui.annotation.WheatUserRole
import io.agora.secnceui.annotation.WheatUserStatus

data class SeatInfoBean constructor(
    val index: Int = 0,
    val name: String? = null,
    val avatar: String = "",
    val userId: String? = null,
    @WheatSeatType val wheatSeatType: Int = WheatSeatType.Idle,
    @WheatUserRole val userRole: Int = WheatUserRole.None,
    @WheatUserStatus val userStatus: Int = WheatUserStatus.None,
    @DrawableRes val rotImage: Int = 0,
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