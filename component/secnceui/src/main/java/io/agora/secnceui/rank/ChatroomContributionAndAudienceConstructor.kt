package io.agora.secnceui.rank

import android.content.Context
import io.agora.secnceui.R
import io.agora.secnceui.annotation.AINSModeType
import io.agora.secnceui.annotation.WheatSeatAction
import io.agora.secnceui.annotation.WheatUserRole
import io.agora.secnceui.bean.AINSModeBean
import io.agora.secnceui.bean.AINSSoundsBean
import io.agora.secnceui.bean.AudienceInfoBean
import io.agora.secnceui.bean.ContributionBean

object ChatroomContributionAndAudienceConstructor {

    fun builderContributionRanking(context: Context): MutableList<ContributionBean> {
        return mutableListOf<ContributionBean>().apply {
            add(
                ContributionBean(number = 1, name = "123", avatar = "", userId = "", contributionValue = 2134)
            )
            add(
                ContributionBean(number = 2, name = "233", avatar = "", userId = "", contributionValue = 1999)
            )
            add(
                ContributionBean(number = 3, name = "444", avatar = "", userId = "", contributionValue = 1878)
            )
            add(
                ContributionBean(number = 4, name = "66", avatar = "", userId = "", contributionValue = 1028)
            )
            add(
                ContributionBean(number = 5, name = "88", avatar = "", userId = "", contributionValue = 99)
            )
            add(
                ContributionBean(number = 6, name = "90", avatar = "", userId = "", contributionValue = 0)
            )
        }
    }

    fun builderAudienceList(context: Context): MutableList<AudienceInfoBean> {
        return mutableListOf<AudienceInfoBean>().apply {
            add(
                AudienceInfoBean(name = "123", avatar = "", userId = "", userRole = WheatUserRole.Owner)
            )
            add(
                AudienceInfoBean(
                    name = "233",
                    avatar = "",
                    userId = "",
                    userRole = WheatUserRole.Guest,
                    seatActionType = WheatSeatAction.Invite
                )
            )
            add(
                AudienceInfoBean(
                    name = "444",
                    avatar = "",
                    userId = "",
                    userRole = WheatUserRole.Guest,
                    seatActionType = WheatSeatAction.KickOff
                )
            )
            add(
                AudienceInfoBean(
                    name = "66",
                    avatar = "",
                    userId = "",
                    userRole = WheatUserRole.Guest,
                    seatActionType = WheatSeatAction.Invite
                )
            )
            add(
                AudienceInfoBean(
                    name = "88",
                    avatar = "",
                    userId = "",
                    userRole = WheatUserRole.Guest,
                    seatActionType = WheatSeatAction.Invite
                )
            )
            add(
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
}