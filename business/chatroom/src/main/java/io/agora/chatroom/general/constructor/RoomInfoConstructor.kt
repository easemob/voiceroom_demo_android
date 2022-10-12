package io.agora.chatroom.general.constructor

import android.text.TextUtils
import com.google.gson.reflect.TypeToken
import io.agora.buddy.tool.GsonTools
import io.agora.chatroom.bean.RoomKitBean
import io.agora.chatroom.general.repositories.ProfileManager
import io.agora.config.ConfigConstants
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
        isOwner = curUserIsHost(roomInfo.owner?.uid)
    }

    fun RoomKitBean.convertByRoomDetailInfo(roomDetails: VRoomInfoBean.VRoomDetail) {
        roomId = roomDetails.room_id ?: ""
        chatroomId = roomDetails.chatroom_id ?: ""
        channelId = roomDetails.channel_id ?: ""
        ownerId = roomDetails.owner?.uid ?: ""
        roomType = roomDetails.type
        isOwner = curUserIsHost(roomDetails.owner?.uid)
    }

    private fun curUserIsHost(ownerId: String?): Boolean {
        return TextUtils.equals(ownerId, ProfileManager.getInstance().profile.uid)
    }

    /**
     * 服务端roomInfo bean 转 ui bean
     */
    fun serverRoomInfo2UiRoomInfo(roomDetail: VRoomInfoBean.VRoomDetail): RoomInfoBean {
        val roomInfo = RoomInfoBean().apply {
            channelId = roomDetail.channel_id ?: ""
            chatroomName = roomDetail.name ?: ""
            owner = serverUser2UiUser(roomDetail.owner)
            memberCount = roomDetail.member_count
            giftCount = roomDetail.gift_amount
            watchCount = roomDetail.click_count
            soundSelection = roomDetail.soundSelection
            roomType = roomDetail.type
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

    private fun serverUser2UiUser(vUser: VMemberBean?): RoomUserInfoBean? {
        if (vUser == null) return null
        return RoomUserInfoBean().apply {
            userId = vUser.uid ?: ""
            chatUid = vUser.chat_uid ?: ""
            rtcUid = vUser.rtc_uid
            username = vUser.name ?: ""
            userAvatar = vUser.portrait ?: ""
        }
    }

    private fun serverRoomRankUserToUiBean(vUser: VRankingMemberBean?): RoomRankUserBean? {
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
    fun convertMicUiBean(vRoomMicInfoList: List<VRMicBean>, roomType: Int, ownerUid: String): List<MicInfoBean> {
        val micInfoList = mutableListOf<MicInfoBean>()
        val interceptIndex = if (roomType == ConfigConstants.RoomType.Common_Chatroom) 5 else 4
        for (i in vRoomMicInfoList.indices) {
            if (i > interceptIndex) break
            val serverMicInfo = vRoomMicInfoList[i]
            val micInfo = MicInfoBean().apply {
                index = serverMicInfo.mic_index
                serverMicInfo.member?.let { roomUser ->
                    userInfo = serverUser2UiUser(roomUser)
                    ownerTag = !TextUtils.isEmpty(ownerUid) && TextUtils.equals(ownerUid, roomUser.uid)
                    // 有人默认显示音量柱
                    audioVolumeType = ConfigConstants.VolumeType.Volume_None
                }
            }
            micInfo.micStatus = serverMicInfo.status
            micInfoList.add(micInfo)
        }
        return micInfoList
    }

    /**
     * im kv 属性转换ui bean
     */
    fun convertAttrMicUiBean(attributeMap: Map<String, String>, ownerUid: String): Map<Int, MicInfoBean> {
        val micInfoMap = mutableMapOf<Int, MicInfoBean>()
        attributeMap.entries.forEach { entry ->
            var index = ConfigConstants.MicConstant.micMap[entry.key]
            if (index == null) index = -1
            GsonTools.toBean<VRMicBean>(entry.value, object : TypeToken<VRMicBean>() {}.type)?.let { attrBean ->
                val micInfo = MicInfoBean().apply {
                    this.index = index
                    this.micStatus = attrBean.status
                    attrBean.member?.let { roomUser ->
                        userInfo = serverUser2UiUser(roomUser)
                        ownerTag = !TextUtils.isEmpty(ownerUid) && TextUtils.equals(ownerUid, roomUser.uid)
                    }
                }
                micInfoMap[index] = micInfo
            }
        }
        return micInfoMap
    }

    fun convertServerRankToUiRank(rankList: List<VRankingMemberBean>): List<RoomRankUserBean> {
        val rankUsers = mutableListOf<RoomRankUserBean>()

        for (i in rankList.indices) {
            // 取前三名
            if (i > 2) break
            serverRoomRankUserToUiBean(rankList[i])?.let { rankUser ->
                rankUsers.add(rankUser)
            }
        }
        return rankUsers
    }
}