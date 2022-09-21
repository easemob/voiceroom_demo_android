package io.agora.chatroom.general.constructor

import io.agora.secnceui.annotation.MicStatus
import io.agora.secnceui.bean.*
import tools.bean.*

/**
 * @author create by zhangwei03
 */
object RoomInfoConstructor {

    // 机器人位置
    private const val botIndex = 6

    /**
     * 服务端roomInfo bean 转 ui bean
     */
    fun serverRoomInfo2UiRoomInfo(vRoomInfo: VRoomInfoBean): RoomInfoBean {
        val roomInfo = RoomInfoBean().apply {
            channelId = vRoomInfo.room?.channel_id ?: ""
            chatroomName = vRoomInfo.room?.name ?: ""
            notice = vRoomInfo.room?.announcement ?: ""
            owner = serverUser2UiUser(vRoomInfo.room?.owner)
            memberCount = vRoomInfo.room?.member_count ?: 0
            giftCount = vRoomInfo.room?.gift_amount ?: 0
            watchCount = vRoomInfo.room?.click_count ?: 0
        }
        vRoomInfo.room?.ranking_list?.let { rankList ->
            val rankUsers = mutableListOf<RoomRankUserBean>()
            for (i in rankList.indices) {
                // 取前三名
                if (i > 2) break
                serverRoomRankUserToUiBean(rankList[i])?.let { rankUser ->
                    rankUsers.add(rankUser)
                }
            }
            roomInfo.topRankUsers = rankUsers
        }
        return roomInfo
    }

    private fun serverUser2UiUser(vUser: VRoomUser?): RoomUserInfoBean? {
        if (vUser == null) return null
        return RoomUserInfoBean().apply {
            userId = vUser.uid ?: ""
            chatUid = vUser.chat_uid ?: ""
            rtcUid = vUser.rtcUid
            username = vUser.name ?: ""
            userAvatar = vUser.portrait ?: ""
        }
    }

    private fun serverRoomRankUserToUiBean(vUser: VRoomRanking?): RoomRankUserBean? {
        if (vUser == null) return null
        return RoomRankUserBean().apply {
            username = vUser.name ?: ""
            userAvatar = vUser.portrait ?: ""
            amount = vUser.amount
        }
    }

    /**
     * 服务端roomInfo bean 转 麦位 ui bean
     */
    fun convertMicUiBean(roomInfo: VRoomInfoBean): List<MicInfoBean> {
        val micInfoList = mutableListOf<MicInfoBean>()
        roomInfo.mic_info?.let {
            for (i in it.indices) {
                if (i > 5) break
                val serverMicInfo = it[i]
                val micInfo = MicInfoBean().apply {
                    index = serverMicInfo.index
                    serverMicInfo.user?.let { roomUser ->
                        userInfo = serverUser2UiUser(roomUser)

                    }
                }
                micInfo.micStatus = serverMicInfo.status
                micInfoList.add(micInfo)
            }
        }
        return micInfoList
    }

    fun isBotActive(roomInfo: VRoomInfoBean):Boolean{
        val status = roomInfo.mic_info?.get(botIndex)?.status
        return status == MicStatus.BotActivated
    }
}