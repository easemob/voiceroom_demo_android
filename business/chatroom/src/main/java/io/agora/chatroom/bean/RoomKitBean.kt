package io.agora.chatroom.bean

/**
 * @author create by zhangwei03
 */
data class RoomKitBean constructor(
    var roomId: String = "",
    var channelId: String = "",
    var chatroomId: String = "",
    var ownerId: String = "",
    var roomType: Int = 0,
    var isOwner: Boolean = false
)