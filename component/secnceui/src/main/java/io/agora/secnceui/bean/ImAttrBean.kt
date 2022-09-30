package io.agora.secnceui.bean

import io.agora.secnceui.annotation.MicStatus

/**
 * @author create by zhangwei03
 */
data class ImAttrBean(
    val uid: String?=null,
    var status: Int = MicStatus.Idle
)
