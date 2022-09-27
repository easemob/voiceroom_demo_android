package io.agora.secnceui.bean

import io.agora.secnceui.annotation.SoundSelectionType

data class RoomInfoBean constructor(
    var roomId: String = "",
    var channelId: String = "",
    var chatroomId: String = "",
    var chatroomName: String = "",
    var owner: RoomUserInfoBean? = null,
    var memberCount: Int = 0,
    var giftCount: Int = 0,
    var watchCount: Int = 0,
    @SoundSelectionType var soundSelection: Int = SoundSelectionType.SocialChat,
    var topRankUsers: List<RoomRankUserBean> = emptyList(), // 前三名
) : BaseRoomBean