package io.agora.secnceui.bean

data class ChatroomInfoBean constructor(
    var channelId: String = "",
    var chatroomName: String = "",
    var ownerUserId: String = "",
    var ownerName: String = "",
    var ownerAvatar: String = "",
    val topGifts: List<ChatroomUserInfoBean>? = null, // 前三名
    var audiencesCount: Int = 0,
    var giftCount: Int = 0,
    var watchCount: Int = 0,
) : BaseChatroomBean