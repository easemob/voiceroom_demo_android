package io.agora.secnceui.bean

import io.agora.secnceui.annotation.WheatSeatAction
import io.agora.secnceui.annotation.WheatUserRole

data class ContributionBean constructor(
    val number: Int = 1,
    val name: String? = null,
    val avatar: String = "",
    val userId: String? = null,
    val contributionValue: Int
) : BaseChatroomBean

data class AudienceInfoBean constructor(
    val name: String,
    val avatar: String = "",
    val userId: String? = null,
    @WheatUserRole val userRole: Int = WheatUserRole.Audience,
    @WheatSeatAction var seatActionType: Int = WheatSeatAction.Invite
) : BaseChatroomBean