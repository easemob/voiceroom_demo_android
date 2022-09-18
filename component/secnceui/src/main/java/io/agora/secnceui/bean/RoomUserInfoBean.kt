package io.agora.secnceui.bean

import io.agora.secnceui.annotation.UserRole

/**
 * @author create by zhangwei03
 */
class RoomUserInfoBean constructor(
    var userId: String = "",
    var chatUid: String = "",
    var rtcUid: Int = 0,
    var username: String = "",
    var userAvatar: String = "",
    @UserRole var userRole: Int = UserRole.None, // 角色
    var mute: Boolean = false //用户是否自己静音
) : BaseRoomBean