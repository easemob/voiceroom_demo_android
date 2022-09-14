package io.agora.secnceui.bean

data class ChatroomInfoBean constructor(
    var chatroomId: String = "",
    var chatroomName: String = "",
    val ownerUserId: String = "",
    val ownerName: String = "",
    val ownerAvatar: String = "",
    val topGifts: List<ChatroomUserInfoBean>? = null, // 前三名
    var audiencesCount: Int = 0,
    var giftCount: Int = 0,
    var watchCount: Int = 0,
) : BaseChatroomBean