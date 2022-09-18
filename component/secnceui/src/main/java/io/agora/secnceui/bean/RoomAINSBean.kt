package io.agora.secnceui.bean

import io.agora.secnceui.annotation.AINSModeType
import io.agora.secnceui.annotation.AINSSoundType

data class AINSModeBean constructor(
    val anisName: String = "",
    @AINSModeType var anisMode: Int = AINSModeType.Off
) : BaseRoomBean

data class AINSSoundsBean constructor(
    val soundName: String = "",
    val soundSubName: String = "",
    @AINSSoundType var soundsType: Int = AINSSoundType.None
) : BaseRoomBean