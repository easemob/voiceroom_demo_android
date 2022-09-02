package io.agora.secnceui.wheat

import android.content.Context
import io.agora.secnceui.R
import io.agora.secnceui.annotation.WheatSeatAction
import io.agora.secnceui.annotation.WheatSeatType
import io.agora.secnceui.annotation.WheatUserRole
import io.agora.secnceui.annotation.WheatUserStatus
import io.agora.secnceui.bean.*

object ChatroomWheatConstructor {

    fun builder2dSeatList(): MutableList<SeatInfoBean> {
        return mutableListOf<SeatInfoBean>().apply {
            add(
                SeatInfoBean(
                    index = 0,
                    name = "Susan Stark",
                    avatar = "https://img0.baidu.com/it/u=2400624116,330206631&fm=253&fmt=auto&app=138&f=JPEG?w=400&h=693",
                    wheatSeatType = WheatSeatType.Normal,
                    userRole = WheatUserRole.Owner,
                    userStatus = WheatUserStatus.Speaking,
                    rotImage = 0
                )
            )
            add(SeatInfoBean(index = 1))
            add(SeatInfoBean(index = 2, wheatSeatType = WheatSeatType.Mute))
            add(SeatInfoBean(index = 3, wheatSeatType = WheatSeatType.Lock))
            add(
                SeatInfoBean(
                    index = 4,
                    name = "Jim Scofield",
                    avatar = "https://ss0.baidu.com/7Po3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/a5c27d1ed21b0ef4bc9481e2dcc451da81cb3e09.jpg",
                    wheatSeatType = WheatSeatType.NormalMute,
                    userRole = WheatUserRole.Guest,
                    userStatus = WheatUserStatus.Mute
                )
            )
            add(SeatInfoBean(index = 5, wheatSeatType = WheatSeatType.LockMute))
        }
    }

    fun builder2dBotSeatList(): MutableList<BotSeatInfoBean> {
        return mutableListOf<BotSeatInfoBean>().apply {
            val blueBot = SeatInfoBean(
                index = 0,
                wheatSeatType = WheatSeatType.Inactive,
                userRole = WheatUserRole.Robot,
                userStatus = WheatUserStatus.None,
                name = "Agora Blue",
                rotImage = R.drawable.icon_seat_blue_robot,
            )
            val redBot = SeatInfoBean(
                index = 1,
                wheatSeatType = WheatSeatType.Inactive,
                userRole = WheatUserRole.Robot,
                userStatus = WheatUserStatus.None,
                name = "Agora Red",
                rotImage = R.drawable.icon_seat_red_robot,
            )
            add(BotSeatInfoBean(blueBot, redBot))
        }
    }

    /**
     * 房主点击麦位管理
     */
    fun builderOwnerSeatMangerList(context: Context, seatInfo: SeatInfoBean): MutableList<SeatManagerBean> {
        return mutableListOf<SeatManagerBean>().apply {
            when (seatInfo.wheatSeatType) {
                // 空置-开放
                WheatSeatType.Idle -> {
                    add(SeatManagerBean(context.getString(R.string.chatroom_invite), true, WheatSeatAction.Invite))
                    add(SeatManagerBean(context.getString(R.string.chatroom_mute), true, WheatSeatAction.Mute))
                    add(SeatManagerBean(context.getString(R.string.chatroom_block), true, WheatSeatAction.Block))
                }
                // 空置-座位禁⻨
                WheatSeatType.Mute -> {
                    add(SeatManagerBean(context.getString(R.string.chatroom_invite), true, WheatSeatAction.Invite))
                    add(SeatManagerBean(context.getString(R.string.chatroom_unmute), true, WheatSeatAction.Mute))
                    add(SeatManagerBean(context.getString(R.string.chatroom_block), true, WheatSeatAction.Block))
                }
                // 空置-座位关闭
                WheatSeatType.Lock -> {
                    add(SeatManagerBean(context.getString(R.string.chatroom_invite), false, WheatSeatAction.Invite))
                    add(SeatManagerBean(context.getString(R.string.chatroom_mute), true, WheatSeatAction.Mute))
                    add(SeatManagerBean(context.getString(R.string.chatroom_unblock), true, WheatSeatAction.UnBlock))
                }
                // 空置-座位禁⻨&座位关闭
                WheatSeatType.LockMute -> {
                    add(SeatManagerBean(context.getString(R.string.chatroom_invite), false, WheatSeatAction.Invite))
                    add(SeatManagerBean(context.getString(R.string.chatroom_unmute), true, WheatSeatAction.Mute))
                    add(SeatManagerBean(context.getString(R.string.chatroom_unblock), true, WheatSeatAction.UnBlock))
                }
                // 有⼈-正常
                WheatSeatType.Normal -> {
                    if (seatInfo.userRole == WheatUserRole.Owner) {
                        add(SeatManagerBean(context.getString(R.string.chatroom_mute), true, WheatSeatAction.Mute))
                    } else {
                        add(
                            SeatManagerBean(
                                context.getString(R.string.chatroom_kickoff), true, WheatSeatAction.KickOff
                            )
                        )
                        add(SeatManagerBean(context.getString(R.string.chatroom_mute), true, WheatSeatAction.Mute))
                        add(SeatManagerBean(context.getString(R.string.chatroom_block), true, WheatSeatAction.Block))
                    }
                }
                // 有⼈-禁麦
                WheatSeatType.NormalMute -> {
                    if (seatInfo.userRole == WheatUserRole.Owner) {
                        add(SeatManagerBean(context.getString(R.string.chatroom_mute), true, WheatSeatAction.Mute))
                    } else {
                        add(
                            SeatManagerBean(context.getString(R.string.chatroom_kickoff), true, WheatSeatAction.KickOff)
                        )
                        add(SeatManagerBean(context.getString(R.string.chatroom_unmute), true, WheatSeatAction.Mute))
                        add(SeatManagerBean(context.getString(R.string.chatroom_block), true, WheatSeatAction.Block))
                    }
                }
                else -> {
                    // nothing
                }

            }
        }
    }

    /**
     * 嘉宾点击麦位管理
     */
    fun builderGuestSeatMangerList(context: Context, seatInfo: SeatInfoBean): MutableList<SeatManagerBean> {
        return mutableListOf<SeatManagerBean>().apply {
            when (seatInfo.wheatSeatType) {
                // 有⼈-正常
                WheatSeatType.Normal -> {
                    add(SeatManagerBean(context.getString(R.string.chatroom_mute), true, WheatSeatAction.Mute))
                    add(SeatManagerBean(context.getString(R.string.chatroom_off_stage), true, WheatSeatAction.OffStage))
                }
                // 有⼈-禁麦
                WheatSeatType.NormalMute -> {
                    // 被房主强制静音
                    if (seatInfo.userStatus == WheatUserStatus.ForceMute) {
                        add(SeatManagerBean(context.getString(R.string.chatroom_unmute), false, WheatSeatAction.Mute))
                    } else {
                        add(SeatManagerBean(context.getString(R.string.chatroom_unmute), true, WheatSeatAction.Mute))
                    }
                    add(SeatManagerBean(context.getString(R.string.chatroom_off_stage), true, WheatSeatAction.OffStage))
                }
                else -> {
                    // nothing
                }

            }
        }
    }
}