package io.agora.secnceui.ui.rank

import android.content.Context
import io.agora.secnceui.annotation.MicClickAction
import io.agora.secnceui.annotation.UserRole
import io.agora.secnceui.bean.AudienceInfoBean
import io.agora.secnceui.bean.ContributionBean

object RoomContributionAndAudienceConstructor {

    fun builderContributionRanking(context: Context): MutableList<ContributionBean> {
        return mutableListOf(
            ContributionBean(number = 1, name = "123", avatar = "", userId = "", contributionValue = 2134),
            ContributionBean(number = 2, name = "233", avatar = "", userId = "", contributionValue = 1999),
            ContributionBean(number = 3, name = "444", avatar = "", userId = "", contributionValue = 1878),
            ContributionBean(number = 4, name = "66", avatar = "", userId = "", contributionValue = 1028),
            ContributionBean(number = 5, name = "88", avatar = "", userId = "", contributionValue = 99),
            ContributionBean(number = 6, name = "90", avatar = "", userId = "", contributionValue = 0)
        )
    }

    fun builderAudienceList(context: Context): MutableList<AudienceInfoBean> {
        return mutableListOf(
            AudienceInfoBean(
                name = "123",
                avatar = "",
                userId = "",
                userRole = UserRole.Owner
            ),
            AudienceInfoBean(
                name = "233",
                avatar = "",
                userId = "",
                userRole = UserRole.Guest,
                micClickAction = MicClickAction.Invite
            ),
            AudienceInfoBean(
                name = "444",
                avatar = "",
                userId = "",
                userRole = UserRole.Guest,
                micClickAction = MicClickAction.KickOff
            ),
            AudienceInfoBean(
                name = "66",
                avatar = "",
                userId = "",
                userRole = UserRole.Guest,
                micClickAction = MicClickAction.Invite
            ),
            AudienceInfoBean(
                name = "88",
                avatar = "",
                userId = "",
                userRole = UserRole.Guest,
                micClickAction = MicClickAction.Invite
            ),
            AudienceInfoBean(
                name = "90",
                avatar = "",
                userId = "",
                userRole = UserRole.Guest,
                micClickAction = MicClickAction.Invite
            )
        )
    }
}