package io.agora.secnceui.ui.rank

import android.content.Context
import io.agora.secnceui.annotation.WheatSeatAction
import io.agora.secnceui.annotation.WheatUserRole
import io.agora.secnceui.bean.AudienceInfoBean
import io.agora.secnceui.bean.ContributionBean

object ChatroomContributionAndAudienceConstructor {

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
                userRole = WheatUserRole.Owner
            ),
            AudienceInfoBean(
                name = "233",
                avatar = "",
                userId = "",
                userRole = WheatUserRole.Guest,
                seatActionType = WheatSeatAction.Invite
            ),
            AudienceInfoBean(
                name = "444",
                avatar = "",
                userId = "",
                userRole = WheatUserRole.Guest,
                seatActionType = WheatSeatAction.KickOff
            ),
            AudienceInfoBean(
                name = "66",
                avatar = "",
                userId = "",
                userRole = WheatUserRole.Guest,
                seatActionType = WheatSeatAction.Invite
            ),
            AudienceInfoBean(
                name = "88",
                avatar = "",
                userId = "",
                userRole = WheatUserRole.Guest,
                seatActionType = WheatSeatAction.Invite
            ),
            AudienceInfoBean(
                name = "90",
                avatar = "",
                userId = "",
                userRole = WheatUserRole.Guest,
                seatActionType = WheatSeatAction.Invite
            )
        )
    }
}