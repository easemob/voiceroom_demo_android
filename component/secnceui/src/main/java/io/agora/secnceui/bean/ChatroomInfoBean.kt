package io.agora.secnceui.bean

import io.agora.secnceui.bean.enum.EnumAvatar

data class ChatroomInfoBean constructor(
    var chatroomId: String = "",
    var chatroomName: String = "",
    val ownerUserId: String = "",
    val ownerName: String = "",
    val ownerAvatar: EnumAvatar = EnumAvatar.Man1,
    val topGifts: List<AudienceBean>? = null, // 前三名
    var audiencesCount: Int = 0,
    var giftCount: Int = 0,
    var watchCount: Int = 0,
) : BaseChatroomBean

data class AudienceBean constructor(
    var userid: String = "",
    var username: String = "",
    var userAvatar: EnumAvatar = EnumAvatar.Man1,
)