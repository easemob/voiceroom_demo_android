package io.agora.secnceui.bean

import io.agora.secnceui.annotation.MicClickAction
import io.agora.secnceui.annotation.UserRole

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
    @UserRole val userRole: Int = UserRole.Audience,
    @MicClickAction var micClickAction: Int = MicClickAction.Invite
) : BaseRoomBean