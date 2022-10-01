package io.agora.chatroom.bean

import java.io.Serializable

/**
 * @author create by zhangwei03
 */
data class RoomKitBean constructor(
    var roomId: String = "",
    var channelId: String = "",
    var chatroomId: String = "",
    var ownerId: String = "",
    var roomType: Int = 0,
    var isOwner: Boolean = false //
) : Serializable