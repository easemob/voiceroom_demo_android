package io.agora.secnceui.bean

import io.agora.secnceui.annotation.*

data class MicInfoBean constructor(
    var index: Int = 0,
    var userInfo: RoomUserInfoBean? = null,
    var ownerTag: Boolean = false,
    @MicStatus var micStatus: Int = MicStatus.Idle,
    @AudioVolumeStatus var audioVolume: Int = AudioVolumeStatus.None,
) : BaseRoomBean

data class BotMicInfoBean constructor(
    var blueBot: MicInfoBean,
    var redBot: MicInfoBean
) : BaseRoomBean

data class MicManagerBean constructor(
    val name: String,
    var enable: Boolean = true,
    @MicClickAction var micClickAction: Int = MicClickAction.Invite
) : BaseRoomBean