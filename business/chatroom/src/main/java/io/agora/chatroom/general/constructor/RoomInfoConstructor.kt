package io.agora.chatroom.general.constructor

import android.text.TextUtils
import io.agora.chatroom.bean.RoomKitBean
import io.agora.chatroom.general.repositories.ProfileManager
import io.agora.secnceui.bean.*
import tools.bean.*

/**
 * @author create by zhangwei03
 */
object RoomInfoConstructor {

    fun RoomKitBean.convertByRoomInfo(roomInfo: VRoomBean.RoomsBean) {
        roomId = roomInfo.room_id ?: ""
        chatroomId = roomInfo.chatroom_id ?: ""
        channelId = roomInfo.channel_id ?: ""
        ownerId = roomInfo.owner?.uid ?: ""
        roomType = roomInfo.type
        isOwner = isOwner(roomInfo.owner?.uid)
    }

    fun RoomKitBean.convertByRoomDetailInfo(roomDetails: VRoomDetail) {
        roomId = roomDetails.room_id ?: ""
        chatroomId = roomDetails.chatroom_id ?: ""
        channelId = roomDetails.channel_id ?: ""
        ownerId = roomDetails.owner?.uid ?: ""
        roomType = roomDetails.type
        isOwner = isOwner(roomDetails.owner?.uid)
    }

    fun isOwner(ownerId: String?): Boolean {
        return TextUtils.equals(ownerId, ProfileManager.getInstance().profile.uid)
    }


    /**
     * 服务端roomInfo bean 转 ui bean
     */
    fun serverRoomInfo2UiRoomInfo(roomDetail: VRoomDetail): RoomInfoBean {
        val roomInfo = RoomInfoBean().apply {
            channelId = roomDetail.channel_id ?: ""
            chatroomName = roomDetail.name ?: ""
            owner = serverUser2UiUser(roomDetail.owner)
            memberCount = roomDetail.member_count ?: 0
            giftCount = roomDetail.gift_amount ?: 0
            watchCount = roomDetail.click_count ?: 0
            soundSelection = roomDetail.getSoundSelection()
        }
        roomDetail.ranking_list?.let { rankList ->
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
    fun convertMicUiBean(vRoomMicInfoList: List<VRoomMicInfo>): List<MicInfoBean> {
        val micInfoList = mutableListOf<MicInfoBean>()
        for (i in vRoomMicInfoList.indices) {
            if (i > 5) break
            val serverMicInfo = vRoomMicInfoList[i]
            val micInfo = MicInfoBean().apply {
                index = serverMicInfo.index
                serverMicInfo.user?.let { roomUser ->
                    userInfo = serverUser2UiUser(roomUser)

                }
            }
            micInfo.micStatus = serverMicInfo.status
            micInfoList.add(micInfo)
        }
        return micInfoList
    }
}