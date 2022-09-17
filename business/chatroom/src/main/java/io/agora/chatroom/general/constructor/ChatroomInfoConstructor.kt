package io.agora.chatroom.general.constructor

import io.agora.secnceui.annotation.WheatSeatType
import io.agora.secnceui.annotation.WheatUserStatus
import io.agora.secnceui.bean.*
import tools.bean.VRoomInfoBean
import tools.bean.VRoomMicInfo

/**
 * @author create by zhangwei03
 */
object ChatroomInfoConstructor {

    fun convertTopUiBean(roomInfo: VRoomInfoBean): ChatroomInfoBean {
        return ChatroomInfoBean().apply {
            channelId = roomInfo.room?.channel_id ?: ""
            chatroomName = roomInfo.room?.name ?: ""
            owner.apply {
                userId = roomInfo.room?.owner?.uid ?: ""
                chatUid = roomInfo.room?.owner?.chat_uid ?: ""
                rtcUid = roomInfo.room?.owner?.rtcUid ?: 0
                username = roomInfo.room?.owner?.name ?: ""
                userAvatar = roomInfo.room?.owner?.portrait ?: ""
            }
            memberCount = roomInfo.room?.member_count ?: 0
            giftCount = roomInfo.room?.gift_amount ?: 0
            watchCount = roomInfo.room?.click_count ?: 0
        }
    }

    fun convertMicUiBean(roomInfo: VRoomInfoBean): List<SeatInfoBean> {
        val seatInfoList = mutableListOf<SeatInfoBean>()
        roomInfo.mic_info?.let {
            for (i in it.indices) {
                if (i > 5) break
                val micInfo = it[i]
                val seatInfo = SeatInfoBean().apply {
                    index = micInfo.index
                    micInfo.user?.let { roomUser ->
                        userInfo = ChatroomUserInfoBean().apply {
                            userId = roomUser.uid ?: ""
                            chatUid = roomUser.chat_uid ?: ""
                            rtcUid = roomUser.rtcUid
                            username = roomUser.name ?: ""
                            userAvatar = roomUser.portrait ?: ""
                        }
                    }
                }
                convertWheatStatus(micInfo,seatInfo)
                seatInfoList.add(seatInfo)
            }
        }
        return seatInfoList
    }

    /**
     * 0:正常状态 1:闭麦 2:禁言 3:锁麦 4:锁麦和禁言 -1:空闲
     */

    private fun convertWheatStatus(micInfo: VRoomMicInfo, seatInfoBean: SeatInfoBean) {
        when (micInfo.status) {
            0 -> seatInfoBean.wheatSeatType = WheatSeatType.Normal
            1 ->
                if (micInfo.user == null) WheatSeatType.Mute else WheatSeatType.NormalMute
            2 -> {
                if (micInfo.user == null) WheatSeatType.Mute else WheatSeatType.NormalMute
                seatInfoBean.userStatus = WheatUserStatus.ForceMute
            }
            3 -> seatInfoBean.wheatSeatType = WheatSeatType.Lock
            4 -> seatInfoBean.wheatSeatType = WheatSeatType.LockMute
            else -> seatInfoBean.wheatSeatType = WheatSeatType.Idle
        }
    }

    fun convertMicBotUiBean(roomInfo: VRoomInfoBean): BotSeatInfoBean {
        val botSeatInfoBean = BotSeatInfoBean(SeatInfoBean(), SeatInfoBean())
        roomInfo.mic_info?.let {
            for (i in it.indices) {
                if (i == 6) {
                    it[i].user?.let { roomUser ->
                        botSeatInfoBean.blueBot.userInfo = ChatroomUserInfoBean().apply {
                            userId = roomUser.uid ?: ""
                            chatUid = roomUser.chat_uid ?: ""
                            rtcUid = roomUser.rtcUid
                            username = roomUser.name ?: ""
                            userAvatar = roomUser.portrait ?: ""
                        }
                        convertWheatStatus(it[i],botSeatInfoBean.blueBot)
                    }
                } else if (i == 7) {
                    it[i].user?.let { roomUser ->
                        botSeatInfoBean.redBot.userInfo = ChatroomUserInfoBean().apply {
                            userId = roomUser.uid ?: ""
                            chatUid = roomUser.chat_uid ?: ""
                            rtcUid = roomUser.rtcUid
                            username = roomUser.name ?: ""
                            userAvatar = roomUser.portrait ?: ""
                        }
                        convertWheatStatus(it[i],botSeatInfoBean.redBot)
                    }
                } else {
                    continue
                }
            }
        }
        return botSeatInfoBean
    }
}