package io.agora.secnceui.bean

import io.agora.config.ConfigConstants

data class AINSModeBean constructor(
    val anisName: String = "",
    var anisMode: Int = ConfigConstants.AINSMode.AINS_Medium // 默认
) : BaseRoomBean

data class AINSSoundsBean constructor(
    val soundType: Int = ConfigConstants.AINSSoundType.AINS_TVSound,
    val soundName: String = "",
    val soundSubName: String = "",
    var soundMode: Int = ConfigConstants.AINSMode.AINS_Unknown
) : BaseRoomBean