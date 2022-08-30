package io.agora.secnceui.bean

import androidx.annotation.DrawableRes

data class SoundSelectionBean(
    val soundTitle: String = "",
    val soundName: String = "",
    val soundIntroduce: String = "",
    var isCurrentUsing: Boolean = false,
    var isShowHint: Boolean = false,
    val customer: List<CustomerUsageBean>? = null
) : BaseChatroomBean

data class CustomerUsageBean(
    val name: String? = "",
    @DrawableRes val avatar: Int = 0
) : BaseChatroomBean