package com.easemob.secnceui.bean

import com.easemob.config.ConfigConstants
import com.easemob.secnceui.annotation.MicClickAction
import com.easemob.secnceui.annotation.MicStatus

data class MicInfoBean constructor(
    var index: Int = 0,
    var userInfo: RoomUserInfoBean? = null,
    var ownerTag: Boolean = false,
    @MicStatus var micStatus: Int = MicStatus.Idle,
    var audioVolumeType: Int = ConfigConstants.VolumeType.Volume_None,
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