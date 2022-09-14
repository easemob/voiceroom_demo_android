package io.agora.secnceui.ui.wheat

import android.content.Context
import io.agora.secnceui.R
import io.agora.secnceui.annotation.*
import io.agora.secnceui.bean.*
import io.agora.secnceui.constants.ScenesConstant

object ChatroomWheatConstructor {

    fun builder2dSeatList(): MutableList<SeatInfoBean> {

        return mutableListOf(
            SeatInfoBean(
                index = 0,
                name = "Susan Stark",
                avatar = "avatar6",
                wheatSeatType = WheatSeatType.Normal,
                userRole = WheatUserRole.Owner,
                userStatus = WheatUserStatus.Speaking,
            ),
            SeatInfoBean(index = 1),
            SeatInfoBean(index = 2, wheatSeatType = WheatSeatType.Mute),
            SeatInfoBean(index = 3, wheatSeatType = WheatSeatType.Lock),
            SeatInfoBean(
                index = 4,
                name = "Jim Scofield",
                avatar = "avatar18",
                wheatSeatType = WheatSeatType.NormalMute,
                userRole = WheatUserRole.Guest,
                userStatus = WheatUserStatus.Mute
            ),
            SeatInfoBean(index = 5, wheatSeatType = WheatSeatType.LockMute)
        )
    }

    fun builder2dBotSeatList(context: Context): MutableList<BotSeatInfoBean> {
        val blueBot = SeatInfoBean(
            index = 0,
            wheatSeatType = WheatSeatType.Inactive,
            userRole = WheatUserRole.Robot,
            userStatus = WheatUserStatus.None,
            name = context.getString(R.string.chatroom_agora_blue),
            avatar = "icon_seat_blue_robot",
        )
        val redBot = SeatInfoBean(
            index = 1,
            wheatSeatType = WheatSeatType.Inactive,
            userRole = WheatUserRole.Robot,
            userStatus = WheatUserStatus.None,
            name = context.getString(R.string.chatroom_agora_red),
            avatar = "icon_seat_red_robot",
        )
        return mutableListOf(BotSeatInfoBean(blueBot, redBot))
    }

    fun builder3dSeatMap(context: Context): MutableMap<String, SeatInfoBean> {

        return mutableMapOf(
            ScenesConstant.KeySeat0 to SeatInfoBean(
                index = 0,
                name = "Susan Stark",
                avatar = "avatar11",
                wheatSeatType = WheatSeatType.Normal,
                userRole = WheatUserRole.Owner,
                userStatus = WheatUserStatus.Speaking,
            ),
            ScenesConstant.KeySeat1 to SeatInfoBean(index = 1),
            ScenesConstant.KeySeatBlue to SeatInfoBean(
                index = 2,
                wheatSeatType = WheatSeatType.Inactive,
                userRole = WheatUserRole.Robot,
                userStatus = WheatUserStatus.None,
                name = context.getString(R.string.chatroom_agora_blue),
                avatar = "icon_seat_blue_robot",
            ),
            ScenesConstant.KeySeat3 to SeatInfoBean(index = 3, wheatSeatType = WheatSeatType.Mute),
            ScenesConstant.KeySeat4 to SeatInfoBean(index = 4, wheatSeatType = WheatSeatType.Mute),

            ScenesConstant.KeySeatRed to SeatInfoBean(
                index = 5,
                wheatSeatType = WheatSeatType.Inactive,
                userRole = WheatUserRole.Robot,
                userStatus = WheatUserStatus.None,
                name = context.getString(R.string.chatroom_agora_red),
                avatar = "icon_seat_red_robot",
            ),
            ScenesConstant.KeySeatCenter to SeatInfoBean(
                index = 6,
                name = "Jim Scofield",
                avatar = "avatar12",
                wheatSeatType = WheatSeatType.NormalMute,
                userRole = WheatUserRole.Guest,
                userStatus = WheatUserStatus.Mute
            ),
        )
    }

    /**
     * 房主点击麦位管理
     */
    fun builderOwnerSeatMangerList(context: Context, seatInfo: SeatInfoBean): MutableList<SeatManagerBean> {
        return when (seatInfo.wheatSeatType) {
            // 空置-开放
            WheatSeatType.Idle -> {
                mutableListOf(
                    SeatManagerBean(context.getString(R.string.chatroom_invite), true, WheatSeatAction.Invite),
                    SeatManagerBean(context.getString(R.string.chatroom_mute), true, WheatSeatAction.Mute),
                    SeatManagerBean(context.getString(R.string.chatroom_block), true, WheatSeatAction.Block)
                )
            }
            // 空置-座位禁⻨
            WheatSeatType.Mute -> {
                mutableListOf(
                    SeatManagerBean(context.getString(R.string.chatroom_invite), true, WheatSeatAction.Invite),
                    SeatManagerBean(context.getString(R.string.chatroom_unmute), true, WheatSeatAction.Mute),
                    SeatManagerBean(context.getString(R.string.chatroom_block), true, WheatSeatAction.Block)
                )
            }
            // 空置-座位关闭
            WheatSeatType.Lock -> {
                mutableListOf(
                    SeatManagerBean(context.getString(R.string.chatroom_invite), false, WheatSeatAction.Invite),
                    SeatManagerBean(context.getString(R.string.chatroom_mute), true, WheatSeatAction.Mute),
                    SeatManagerBean(context.getString(R.string.chatroom_unblock), true, WheatSeatAction.UnBlock)
                )
            }
            // 空置-座位禁⻨&座位关闭
            WheatSeatType.LockMute -> {
                mutableListOf(
                    SeatManagerBean(context.getString(R.string.chatroom_invite), false, WheatSeatAction.Invite),
                    SeatManagerBean(context.getString(R.string.chatroom_unmute), true, WheatSeatAction.Mute),
                    SeatManagerBean(context.getString(R.string.chatroom_unblock), true, WheatSeatAction.UnBlock)
                )
            }
            // 有⼈-正常
            WheatSeatType.Normal -> {
                if (seatInfo.userRole == WheatUserRole.Owner) {
                    mutableListOf(
                        SeatManagerBean(context.getString(R.string.chatroom_mute), true, WheatSeatAction.Mute)
                    )
                } else {
                    mutableListOf(
                        SeatManagerBean(context.getString(R.string.chatroom_kickoff), true, WheatSeatAction.KickOff),
                        SeatManagerBean(context.getString(R.string.chatroom_mute), true, WheatSeatAction.Mute),
                        SeatManagerBean(context.getString(R.string.chatroom_block), true, WheatSeatAction.Block)
                    )
                }
            }
            // 有⼈-禁麦
            WheatSeatType.NormalMute -> {
                if (seatInfo.userRole == WheatUserRole.Owner) {
                    mutableListOf(
                        SeatManagerBean(context.getString(R.string.chatroom_mute), true, WheatSeatAction.Mute)
                    )
                } else {
                    mutableListOf(
                        SeatManagerBean(context.getString(R.string.chatroom_kickoff), true, WheatSeatAction.KickOff),
                        SeatManagerBean(context.getString(R.string.chatroom_unmute), true, WheatSeatAction.Mute),
                        SeatManagerBean(context.getString(R.string.chatroom_block), true, WheatSeatAction.Block)
                    )
                }
            }
            else -> mutableListOf()

        }
    }

    /**
     * 嘉宾点击麦位管理
     */
    fun builderGuestSeatMangerList(context: Context, seatInfo: SeatInfoBean): MutableList<SeatManagerBean> {
        return when (seatInfo.wheatSeatType) {
            // 有⼈-正常
            WheatSeatType.Normal -> {
                mutableListOf(
                    SeatManagerBean(context.getString(R.string.chatroom_mute), true, WheatSeatAction.Mute),
                    SeatManagerBean(context.getString(R.string.chatroom_off_stage), true, WheatSeatAction.OffStage)
                )
            }
            // 有⼈-禁麦
            WheatSeatType.NormalMute -> {
                // 被房主强制静音
                if (seatInfo.userStatus == WheatUserStatus.ForceMute) {
                    mutableListOf(
                        SeatManagerBean(context.getString(R.string.chatroom_unmute), false, WheatSeatAction.Mute),
                        SeatManagerBean(context.getString(R.string.chatroom_off_stage), true, WheatSeatAction.OffStage)
                    )
                } else {
                    mutableListOf(
                        SeatManagerBean(context.getString(R.string.chatroom_unmute), true, WheatSeatAction.Mute),
                        SeatManagerBean(context.getString(R.string.chatroom_off_stage), true, WheatSeatAction.OffStage)
                    )
                }
            }
            else -> mutableListOf()

        }
    }
}