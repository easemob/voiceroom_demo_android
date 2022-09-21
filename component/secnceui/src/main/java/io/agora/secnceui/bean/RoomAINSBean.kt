package io.agora.secnceui.bean

import io.agora.secnceui.annotation.AINSModeType
import io.agora.secnceui.annotation.AINSSoundType
import io.agora.secnceui.annotation.AINSUser

data class AINSModeBean constructor(
    val anisName: String = "",
    @AINSUser val anisUser: Int = AINSUser.Yours,
    @AINSModeType var anisMode: Int = AINSModeType.Medium // 默认
) : BaseRoomBean

data class AINSSoundsBean constructor(
    val soundName: String = "",
    val soundSubName: String = "",
    @AINSSoundType var soundsType: Int = AINSSoundType.Unknown
) : BaseRoomBean