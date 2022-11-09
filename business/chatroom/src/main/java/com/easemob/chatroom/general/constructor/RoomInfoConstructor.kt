package com.easemob.chatroom.general.constructor

import android.text.TextUtils
import com.google.gson.reflect.TypeToken
import com.easemob.buddy.tool.GsonTools
import com.easemob.chatroom.bean.RoomKitBean
import com.easemob.chatroom.general.repositories.ProfileManager
import com.easemob.config.ConfigConstants
import com.easemob.secnceui.bean.*
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
        soundEffect = roomInfo.soundSelection
    }

    fun RoomKitBean.convertByRoomDetailInfo(roomDetails: VRoomInfoBean.VRoomDetail) {
        roomId = roomDetails.room_id ?: ""
        chatroomId = roomDetails.chatroom_id ?: ""
        channelId = roomDetails.channel_id ?: ""
        ownerId = roomDetails.owner?.uid ?: ""
        roomType = roomDetails.type
        isOwner = curUserIsHost(roomDetails.owner?.uid)
        soundEffect = roomDetails.soundSelection
    }

    private fun curUserIsHost(ownerId: String?): Boolean {
        return TextUtils.equals(ownerId, ProfileManager.getInstance().profile.uid)
    }

    /**
     * 服务端roomInfo bean 转 ui bean
     */
    fun serverRoomInfo2UiRoomInfo(roomDetail: VRoomInfoBean.VRoomDetail): com.easemob.secnceui.bean.RoomInfoBean {
        val roomInfo = com.easemob.secnceui.bean.RoomInfoBean().apply {
            channelId = roomDetail.channel_id ?: ""
            chatroomName = roomDetail.name ?: ""
            owner = serverUser2UiUser(roomDetail.owner)
            memberCount = roomDetail.member_count
            // 普通观众 memberCount +1
            if (owner?.rtcUid != ProfileManager.getInstance().rtcUid()) {
                memberCount += 1
            }
            giftCount = roomDetail.gift_amount
            watchCount = roomDetail.click_count
            soundSelection = roomDetail.soundSelection
            roomType = roomDetail.type
        }
        roomDetail.ranking_list?.let { rankList ->
            val rankUsers = mutableListOf<com.easemob.secnceui.bean.RoomRankUserBean>()
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

    private fun serverRoomRankUserToUiBean(vUser: VRankingMemberBean?): com.easemob.secnceui.bean.RoomRankUserBean? {
        if (vUser == null) return null
        return com.easemob.secnceui.bean.RoomRankUserBean().apply {
            username = vUser.name ?: ""
            userAvatar = vUser.portrait ?: ""
            amount = vUser.amount
        }
    }

    /**
     * 服务端roomInfo bean 转 麦位 ui bean
     */
    fun convertMicUiBean(vRoomMicInfoList: List<VRMicBean>, roomType: Int, ownerUid: String): List<com.easemob.secnceui.bean.MicInfoBean> {
        val micInfoList = mutableListOf<com.easemob.secnceui.bean.MicInfoBean>()
        val interceptIndex = if (roomType == ConfigConstants.RoomType.Common_Chatroom) 5 else 4
        for (i in vRoomMicInfoList.indices) {
            if (i > interceptIndex) break
            val serverMicInfo = vRoomMicInfoList[i]
            val micInfo = com.easemob.secnceui.bean.MicInfoBean().apply {
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
     * im kv 属性转 micInfo
     * key mic0 value micInfo
     */
    fun convertAttr2MicInfoMap(attributeMap: Map<String, String>): Map<String, VRMicBean> {
        val micInfoMap = mutableMapOf<String, VRMicBean>()
        attributeMap.entries.forEach { entry ->
            GsonTools.toBean<VRMicBean>(entry.value, object : TypeToken<VRMicBean>() {}.type)?.let { attrBean ->
                micInfoMap[entry.key] = attrBean
            }
        }
        return micInfoMap
    }

    /**
     * micInfo map转换ui bean
     */
    fun convertMicInfoMap2UiBean(micInfoMap: Map<String, VRMicBean>, ownerUid: String): Map<Int, com.easemob.secnceui.bean.MicInfoBean> {
        val micInfoBeanMap = mutableMapOf<Int, com.easemob.secnceui.bean.MicInfoBean>()
        micInfoMap.entries.forEach { entry ->
            var index = ConfigConstants.MicConstant.micMap[entry.key]
            if (index == null) index = -1
            entry.value.let { attrBean ->
                val micInfo = com.easemob.secnceui.bean.MicInfoBean().apply {
                    this.index = index
                    this.micStatus = attrBean.status
                    attrBean.member?.let { roomUser ->
                        userInfo = serverUser2UiUser(roomUser)
                        ownerTag = !TextUtils.isEmpty(ownerUid) && TextUtils.equals(ownerUid, roomUser.uid)
                    }
                }
                micInfoBeanMap[index] = micInfo
            }
        }
        return micInfoBeanMap
    }

    fun convertServerRankToUiRank(rankList: List<VRankingMemberBean>): List<com.easemob.secnceui.bean.RoomRankUserBean> {
        val rankUsers = mutableListOf<com.easemob.secnceui.bean.RoomRankUserBean>()

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