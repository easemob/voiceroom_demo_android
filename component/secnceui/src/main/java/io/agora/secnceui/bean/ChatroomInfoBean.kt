package io.agora.secnceui.bean

data class ChatroomInfoBean constructor(
    var roomId: String = "",
    var channelId: String = "",
    var chatroomId: String = "",
    var chatroomName: String = "",
    var owner: ChatroomUserInfoBean = ChatroomUserInfoBean(),
    var memberCount: Int = 0,
    var giftCount: Int = 0,
    var watchCount: Int = 0,
    val topGifts: List<ChatroomUserInfoBean> = emptyList(), // 前三名
) : BaseChatroomBean