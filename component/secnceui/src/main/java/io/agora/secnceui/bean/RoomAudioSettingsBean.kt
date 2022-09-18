package io.agora.secnceui.bean

import io.agora.secnceui.annotation.AINSModeType
import io.agora.secnceui.annotation.SoundSelectionType

data class RoomAudioSettingsBean constructor(
    var botOpen: Boolean = false,
    var botVolume: Int = 0,
    @SoundSelectionType var soundSelection: Int = SoundSelectionType.SocialChat,
    @AINSModeType var anisMode: Int = AINSModeType.Off,
    var spatialOpen: Boolean = false,
) : BaseRoomBean

