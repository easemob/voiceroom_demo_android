package io.agora.secnceui.bean

import io.agora.secnceui.annotation.MicClickAction

data class ContributionBean constructor(
    val number: Int = 1,
    val name: String? = null,
    val avatar: String = "",
    val userId: String? = null,
    val contributionValue: Int
) : BaseRoomBean

data class AudienceInfoBean constructor(
    val name: String,
    val avatar: String = "",
    val userId: String? = null,
    val isOwner:Boolean = false,
    @MicClickAction var micClickAction: Int = MicClickAction.Invite
) : BaseRoomBean