package io.agora.secnceui.bean

import io.agora.config.ConfigConstants

data class RoomInfoBean constructor(
    var roomId: String = "",
    var channelId: String = "",
    var chatroomId: String = "",
    var chatroomName: String = "",
    var owner: RoomUserInfoBean? = null,
    var memberCount: Int = 0,
    var giftCount: Int = 0,
    var watchCount: Int = 0,
    var soundSelection: Int = ConfigConstants.SoundSelection.Social_Chat,
    var topRankUsers: List<RoomRankUserBean> = emptyList(), // 前三名
) : BaseRoomBean