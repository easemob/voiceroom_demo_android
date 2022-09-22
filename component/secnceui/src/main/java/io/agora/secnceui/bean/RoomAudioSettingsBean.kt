package io.agora.secnceui.bean

import io.agora.secnceui.annotation.AINSModeType
import io.agora.secnceui.annotation.SoundSelectionType

data class RoomAudioSettingsBean constructor(
    var enable: Boolean = true, // 是否可以点击
    var roomType: Int = 0,
    var botOpen: Boolean = false,
    var botVolume: Int = 50,
    @SoundSelectionType var soundSelection: Int = SoundSelectionType.SocialChat,
    @AINSModeType var anisMode: Int = AINSModeType.Medium,
    var spatialOpen: Boolean = false,
) : BaseRoomBean

