package io.agora.secnceui.wheat

import android.content.Context
import io.agora.secnceui.R
import io.agora.secnceui.bean.*

object ChatroomWheatConstructor {

    fun builder2dSeatList(): MutableList<SeatInfoBean> {
        return mutableListOf<SeatInfoBean>().apply {
            add(
                SeatInfoBean(
                    index = 0,
                    name = "Susan Stark",
                    avatar = "https://img0.baidu.com/it/u=2400624116,330206631&fm=253&fmt=auto&app=138&f=JPEG?w=400&h=693",
                    wheatSeatType = ChatroomWheatSeatType.Normal,
                    userRole = ChatroomWheatUserRole.Owner,
                    userStatus = ChatroomWheatUserStatus.Speaking,
                    rotImage = 0
                )
            )
            add(SeatInfoBean(index = 1))
            add(SeatInfoBean(index = 2, wheatSeatType = ChatroomWheatSeatType.Mute))
            add(SeatInfoBean(index = 3, wheatSeatType = ChatroomWheatSeatType.Lock))
            add(
                SeatInfoBean(
                    index = 4,
                    name = "Jim Scofield",
                    avatar = "https://ss0.baidu.com/7Po3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/a5c27d1ed21b0ef4bc9481e2dcc451da81cb3e09.jpg",
                    wheatSeatType = ChatroomWheatSeatType.NormalMute,
                    userRole = ChatroomWheatUserRole.Guest,
                    userStatus = ChatroomWheatUserStatus.Mute
                )
            )
            add(SeatInfoBean(index = 5, wheatSeatType = ChatroomWheatSeatType.LockMute))
        }
    }

    fun builder2dBotSeatList(): MutableList<BotSeatInfoBean> {
        return mutableListOf<BotSeatInfoBean>().apply {
            val blueBot = SeatInfoBean(
                index = 0,
                wheatSeatType = ChatroomWheatSeatType.Inactive,
                userRole = ChatroomWheatUserRole.Robot,
                userStatus = ChatroomWheatUserStatus.None,
                name = "Agora Blue",
                rotImage = R.drawable.icon_seat_blue_robot,
            )
            val redBot = SeatInfoBean(
                index = 1,
                wheatSeatType = ChatroomWheatSeatType.Inactive,
                userRole = ChatroomWheatUserRole.Robot,
                userStatus = ChatroomWheatUserStatus.None,
                name = "Agora Red",
                rotImage = R.drawable.icon_seat_red_robot,
            )
            add(BotSeatInfoBean(blueBot, redBot))
        }
    }

    fun builderSeatMangerList(context: Context, seatInfo: SeatInfoBean): MutableList<SeatManagerBean> {
        return mutableListOf<SeatManagerBean>().apply {
            when (seatInfo.wheatSeatType) {
                // 空置-开放
                ChatroomWheatSeatType.Idle -> {
                    add(
                        SeatManagerBean(
                            context.getString(R.string.chatroom_invite),
                            true,
                            ChatroomSeatActionType.Invite
                        )
                    )
                    add(SeatManagerBean(context.getString(R.string.chatroom_mute), true, ChatroomSeatActionType.Mute))
                    add(SeatManagerBean(context.getString(R.string.chatroom_block), true, ChatroomSeatActionType.Block))
                }
                // 空置-座位禁⻨
                ChatroomWheatSeatType.Mute -> {
                    add(
                        SeatManagerBean(
                            context.getString(R.string.chatroom_invite),
                            true,
                            ChatroomSeatActionType.Invite
                        )
                    )
                    add(SeatManagerBean(context.getString(R.string.chatroom_unmute), true, ChatroomSeatActionType.Mute))
                    add(SeatManagerBean(context.getString(R.string.chatroom_block), true, ChatroomSeatActionType.Block))
                }
                // 空置-座位关闭
                ChatroomWheatSeatType.Lock -> {
                    add(
                        SeatManagerBean(
                            context.getString(R.string.chatroom_invite),
                            false,
                            ChatroomSeatActionType.Invite
                        )
                    )
                    add(SeatManagerBean(context.getString(R.string.chatroom_mute), true, ChatroomSeatActionType.Mute))
                    add(
                        SeatManagerBean(
                            context.getString(R.string.chatroom_unblock),
                            true,
                            ChatroomSeatActionType.UnBlock
                        )
                    )
                }
                // 空置-座位禁⻨&座位关闭
                ChatroomWheatSeatType.LockMute -> {
                    add(
                        SeatManagerBean(
                            context.getString(R.string.chatroom_invite),
                            false,
                            ChatroomSeatActionType.Invite
                        )
                    )
                    add(SeatManagerBean(context.getString(R.string.chatroom_unmute), true, ChatroomSeatActionType.Mute))
                    add(
                        SeatManagerBean(
                            context.getString(R.string.chatroom_unblock),
                            true,
                            ChatroomSeatActionType.UnBlock
                        )
                    )
                }
                // 有⼈-正常
                ChatroomWheatSeatType.Normal -> {
                    add(
                        SeatManagerBean(
                            context.getString(R.string.chatroom_kick_off),
                            true,
                            ChatroomSeatActionType.Invite
                        )
                    )
                    add(SeatManagerBean(context.getString(R.string.chatroom_mute), true, ChatroomSeatActionType.Mute))
                    add(SeatManagerBean(context.getString(R.string.chatroom_block), true, ChatroomSeatActionType.Block))
                }
                // 有⼈-禁麦
                ChatroomWheatSeatType.NormalMute -> {
                    add(
                        SeatManagerBean(
                            context.getString(R.string.chatroom_kick_off),
                            true,
                            ChatroomSeatActionType.Invite
                        )
                    )
                    add(SeatManagerBean(context.getString(R.string.chatroom_unmute), true, ChatroomSeatActionType.Mute))
                    add(SeatManagerBean(context.getString(R.string.chatroom_block), true, ChatroomSeatActionType.Block))
                }
                else -> {
                    // nothing
                }
            }
        }
    }
}