package io.agora.secnceui.bean

data class RoomInfoBean constructor(
    var roomId: String = "",
    var channelId: String = "",
    var chatroomId: String = "",
    var chatroomName: String = "",
    var owner: RoomUserInfoBean? = null,
    var notice:String = "",
    var memberCount: Int = 0,
    var giftCount: Int = 0,
    var watchCount: Int = 0,
    var topRankUsers: List<RoomRankUserBean> = emptyList(), // 前三名
) : BaseRoomBean