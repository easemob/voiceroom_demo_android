package io.agora.chatroom.general.constructor

import android.text.TextUtils
import io.agora.chatroom.general.repositories.ProfileManager
import io.agora.secnceui.annotation.MicStatus
import io.agora.secnceui.annotation.UserRole
import io.agora.secnceui.bean.*
import tools.bean.*

/**
 * @author create by zhangwei03
 */
object RoomInfoConstructor {

    fun isOwner(vRoomBean: VRoomInfoBean?): Boolean {
        val currentUid = ProfileManager.getInstance().profile?.uid
        return !currentUid.isNullOrEmpty() && TextUtils.equals(currentUid, vRoomBean?.room?.owner?.uid)
    }

    @JvmStatic
    fun isOwner(vRoomBean: VRoomBean.RoomsBean?): Boolean {
        val currentUid = ProfileManager.getInstance().profile?.uid
        return !currentUid.isNullOrEmpty() && TextUtils.equals(currentUid, vRoomBean?.owner?.uid)
    }

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
            rtcUid = vUser.rtcUid ?: 0
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
                        userInfo?.userRole = if (isOwner(roomUser, roomInfo)) {
                            UserRole.Owner
                        } else {
                            UserRole.Guest
                        }
                    }

                }
                convertMicStatus(serverMicInfo, micInfo)
                micInfoList.add(micInfo)
            }
        }
        return micInfoList
    }

    private fun isOwner(user: VRoomUser, roomInfo: VRoomInfoBean): Boolean {
        return !user.uid.isNullOrEmpty() && TextUtils.equals(user.uid, roomInfo.room?.owner?.uid)
    }

    /**
     * 0:正常状态 1:闭麦 2:禁言 3:锁麦 4:锁麦和禁言 -1:空闲
     */
    private fun convertMicStatus(micInfo: VRoomMicInfo, micInfoBean: MicInfoBean) {
        when (micInfo.status) {
            0 -> micInfoBean.micStatus = MicStatus.UserNormal   // 正常状态
            1 -> {
                // 闭麦，用户行为
                micInfoBean.micStatus = MicStatus.UserNormal
                micInfoBean.userInfo?.mute = true
            }
            2 -> {
                // 禁麦
                if (micInfo.user == null) {
                    micInfoBean.micStatus = MicStatus.ForceMute
                } else {
                    micInfoBean.micStatus = MicStatus.UserForceMute
                }
            }
            3 -> micInfoBean.micStatus = MicStatus.Lock  // 锁麦
            4 -> micInfoBean.micStatus = MicStatus.LockForceMute  // 既锁麦又禁麦
            else -> micInfoBean.micStatus = MicStatus.Idle
        }
    }

    fun convertMicBotUiBean(roomInfo: VRoomInfoBean): BotMicInfoBean {
        val blueBotMicInfo = MicInfoBean()
        val redBotMicInfo = MicInfoBean()
        roomInfo.mic_info?.let {
            for (i in it.indices) {
                if (i == 6) {
                    it[i].user?.let { roomUser ->
                        blueBotMicInfo.userInfo = serverUser2UiUser(roomUser)
                        blueBotMicInfo.userInfo?.userRole = UserRole.Robot
                        convertMicStatus(it[i], blueBotMicInfo)
                    }
                } else if (i == 7) {
                    it[i].user?.let { roomUser ->
                        redBotMicInfo.userInfo = serverUser2UiUser(roomUser)
                        redBotMicInfo.userInfo?.userRole = UserRole.Robot
                        convertMicStatus(it[i], redBotMicInfo)
                    }
                } else {
                    continue
                }
            }
        }
        return BotMicInfoBean(blueBotMicInfo, redBotMicInfo)
    }
}