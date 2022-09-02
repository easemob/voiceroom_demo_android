package io.agora.secnceui.bean

import androidx.annotation.DrawableRes
import io.agora.secnceui.annotation.SoundSelectionType

data class SoundSelectionBean constructor(
    @SoundSelectionType val soundSelection: Int = SoundSelectionType.SocialChat,
    val index: Int = 0,
    val soundName: String = "",
    val soundIntroduce: String = "",
    var isCurrentUsing: Boolean = false,
    val customer: List<CustomerUsageBean>? = null
) : BaseChatroomBean

data class CustomerUsageBean constructor(
    val name: String? = "",
    @DrawableRes val avatar: Int = 0
) : BaseChatroomBean