package io.agora.chatroom.general.constructor

import io.agora.secnceui.bean.ChatroomInfoBean
import tools.bean.VRoomInfoBean

/**
 * @author create by zhangwei03
 */
object ChatroomInfoConstructor {

    fun convertUiBean(roomInfo: VRoomInfoBean): ChatroomInfoBean {
        return ChatroomInfoBean().apply {
            channelId = roomInfo.room?.channel_id ?: ""
            chatroomName = roomInfo.room?.name ?: ""
            ownerUserId = roomInfo.room?.owner?.uid ?: ""
            ownerName = roomInfo.room?.owner?.name ?: ""
            ownerAvatar = roomInfo.room?.owner?.portrait ?: ""
            audiencesCount = roomInfo.room?.member_count ?: 0
            giftCount = roomInfo.room?.member_count ?: 0
            watchCount = roomInfo.room?.click_count ?: 0
        }
    }
}