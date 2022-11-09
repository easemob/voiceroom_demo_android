package com.easemob.secnceui.ui.mic

import android.content.Context
import com.easemob.config.ConfigConstants
import com.easemob.secnceui.R
import com.easemob.secnceui.bean.RoomUserInfoBean

internal object RoomMicConstructor {

    fun builderDefault2dMicList(): MutableList<com.easemob.secnceui.bean.MicInfoBean> {
        return mutableListOf(
            com.easemob.secnceui.bean.MicInfoBean(index = 0),
            com.easemob.secnceui.bean.MicInfoBean(index = 1),
            com.easemob.secnceui.bean.MicInfoBean(index = 2),
            com.easemob.secnceui.bean.MicInfoBean(index = 3),
            com.easemob.secnceui.bean.MicInfoBean(index = 4),
            com.easemob.secnceui.bean.MicInfoBean(index = 5)
        )
    }

    fun builderDefault2dBotMicList(context: Context, isUserBot: Boolean = false): MutableList<com.easemob.secnceui.bean.BotMicInfoBean> {
        val blueBot = com.easemob.secnceui.bean.MicInfoBean(
            index = 6,
            micStatus = if (isUserBot) com.easemob.secnceui.annotation.MicStatus.BotActivated else com.easemob.secnceui.annotation.MicStatus.BotInactive,
            audioVolumeType = ConfigConstants.VolumeType.Volume_None,
            userInfo = RoomUserInfoBean().apply {
                username = context.getString(R.string.chatroom_agora_blue)
                userAvatar = "icon_chatroom_blue_robot"
            }
        )
        val redBot = com.easemob.secnceui.bean.MicInfoBean(
            index = 7,
            micStatus = if (isUserBot) com.easemob.secnceui.annotation.MicStatus.BotActivated else com.easemob.secnceui.annotation.MicStatus.BotInactive,
            audioVolumeType = ConfigConstants.VolumeType.Volume_None,
            userInfo = RoomUserInfoBean().apply {
                username = context.getString(R.string.chatroom_agora_red)
                userAvatar = "icon_chatroom_red_robot"
            }
        )
        return mutableListOf(com.easemob.secnceui.bean.BotMicInfoBean(blueBot, redBot))
    }

    fun builderDefault3dMicMap(context: Context, isUserBot: Boolean = false): Map<Int, com.easemob.secnceui.bean.MicInfoBean> {
        return mutableMapOf(
            ConfigConstants.MicConstant.KeyIndex0 to com.easemob.secnceui.bean.MicInfoBean(index = 0),
            ConfigConstants.MicConstant.KeyIndex1 to com.easemob.secnceui.bean.MicInfoBean(index = 1),
            ConfigConstants.MicConstant.KeyIndex2 to com.easemob.secnceui.bean.MicInfoBean(index = 5),
            ConfigConstants.MicConstant.KeyIndex3 to com.easemob.secnceui.bean.MicInfoBean(index = 6),
            // mic4 中间座位
            ConfigConstants.MicConstant.KeyIndex4 to com.easemob.secnceui.bean.MicInfoBean(index = 4),
            ConfigConstants.MicConstant.KeyIndex5 to com.easemob.secnceui.bean.MicInfoBean(
                index = 2,
                micStatus = if (isUserBot) com.easemob.secnceui.annotation.MicStatus.BotActivated else com.easemob.secnceui.annotation.MicStatus.BotInactive,
                audioVolumeType = ConfigConstants.VolumeType.Volume_None,
                userInfo = RoomUserInfoBean().apply {
                    username = context.getString(R.string.chatroom_agora_blue)
                    userAvatar = "icon_chatroom_blue_robot"
                }
            ),
            ConfigConstants.MicConstant.KeyIndex6 to com.easemob.secnceui.bean.MicInfoBean(
                index = 3,
                micStatus = if (isUserBot) com.easemob.secnceui.annotation.MicStatus.BotActivated else com.easemob.secnceui.annotation.MicStatus.BotInactive,
                audioVolumeType = ConfigConstants.VolumeType.Volume_None,
                userInfo = RoomUserInfoBean().apply {
                    username = context.getString(R.string.chatroom_agora_red)
                    userAvatar = "icon_chatroom_red_robot"
                }
            ),
        )
    }

    /**
     * 房主点击麦位管理
     */
    fun builderOwnerMicMangerList(
        context: Context, micInfo: com.easemob.secnceui.bean.MicInfoBean, isMyself: Boolean
    ): MutableList<com.easemob.secnceui.bean.MicManagerBean> {
        return when (micInfo.micStatus) {
            // 正常
            com.easemob.secnceui.annotation.MicStatus.Normal -> {
                if (isMyself) {
                    mutableListOf(
                        com.easemob.secnceui.bean.MicManagerBean(
                            context.getString(R.string.chatroom_mute),
                            true,
                            com.easemob.secnceui.annotation.MicClickAction.Mute
                        )
                    )
                } else {
                    mutableListOf(
                        com.easemob.secnceui.bean.MicManagerBean(
                            context.getString(R.string.chatroom_kickoff),
                            true,
                            com.easemob.secnceui.annotation.MicClickAction.KickOff
                        ),
                        com.easemob.secnceui.bean.MicManagerBean(
                            context.getString(R.string.chatroom_mute),
                            true,
                            com.easemob.secnceui.annotation.MicClickAction.ForceMute
                        ),
                        com.easemob.secnceui.bean.MicManagerBean(
                            context.getString(R.string.chatroom_block),
                            true,
                            com.easemob.secnceui.annotation.MicClickAction.Lock
                        )
                    )
                }
            }
            // 闭麦
            com.easemob.secnceui.annotation.MicStatus.Mute -> {
                if (isMyself) {
                    mutableListOf(
                        com.easemob.secnceui.bean.MicManagerBean(
                            context.getString(R.string.chatroom_unmute),
                            true,
                            com.easemob.secnceui.annotation.MicClickAction.UnMute
                        )
                    )
                } else {
                    mutableListOf(
                        com.easemob.secnceui.bean.MicManagerBean(
                            context.getString(R.string.chatroom_kickoff),
                            true,
                            com.easemob.secnceui.annotation.MicClickAction.KickOff
                        ),
                        com.easemob.secnceui.bean.MicManagerBean(
                            context.getString(R.string.chatroom_unmute),
                            true,
                            com.easemob.secnceui.annotation.MicClickAction.ForceUnMute
                        ),
                        com.easemob.secnceui.bean.MicManagerBean(
                            context.getString(R.string.chatroom_block),
                            true,
                            com.easemob.secnceui.annotation.MicClickAction.Lock
                        )
                    )
                }
            }
            // 禁言 :有人、没人
            com.easemob.secnceui.annotation.MicStatus.ForceMute -> {
                if (micInfo.userInfo == null) {
                    mutableListOf(
                        com.easemob.secnceui.bean.MicManagerBean(
                            context.getString(R.string.chatroom_invite),
                            true,
                            com.easemob.secnceui.annotation.MicClickAction.Invite
                        ),
                        com.easemob.secnceui.bean.MicManagerBean(
                            context.getString(R.string.chatroom_unmute),
                            true,
                            com.easemob.secnceui.annotation.MicClickAction.ForceUnMute
                        ),
                        com.easemob.secnceui.bean.MicManagerBean(
                            context.getString(R.string.chatroom_block),
                            true,
                            com.easemob.secnceui.annotation.MicClickAction.Lock
                        )
                    )
                } else {
                    mutableListOf(
                        com.easemob.secnceui.bean.MicManagerBean(
                            context.getString(R.string.chatroom_kickoff),
                            true,
                            com.easemob.secnceui.annotation.MicClickAction.KickOff
                        ),
                        com.easemob.secnceui.bean.MicManagerBean(
                            context.getString(R.string.chatroom_unmute),
                            true,
                            com.easemob.secnceui.annotation.MicClickAction.ForceUnMute
                        ),
                        com.easemob.secnceui.bean.MicManagerBean(
                            context.getString(R.string.chatroom_block),
                            true,
                            com.easemob.secnceui.annotation.MicClickAction.Lock
                        )
                    )
                }
            }
            // 锁麦
            com.easemob.secnceui.annotation.MicStatus.Lock -> {
                mutableListOf(
                    com.easemob.secnceui.bean.MicManagerBean(
                        context.getString(R.string.chatroom_invite),
                        false,
                        com.easemob.secnceui.annotation.MicClickAction.Invite
                    ),
                    com.easemob.secnceui.bean.MicManagerBean(
                        context.getString(R.string.chatroom_mute),
                        true,
                        com.easemob.secnceui.annotation.MicClickAction.ForceMute
                    ),
                    com.easemob.secnceui.bean.MicManagerBean(
                        context.getString(R.string.chatroom_unblock),
                        true,
                        com.easemob.secnceui.annotation.MicClickAction.UnLock
                    )
                )
            }
            // 锁麦和禁言
            com.easemob.secnceui.annotation.MicStatus.LockForceMute -> {
                mutableListOf(
                    com.easemob.secnceui.bean.MicManagerBean(
                        context.getString(R.string.chatroom_invite),
                        false,
                        com.easemob.secnceui.annotation.MicClickAction.Invite
                    ),
                    com.easemob.secnceui.bean.MicManagerBean(
                        context.getString(R.string.chatroom_unmute),
                        true,
                        com.easemob.secnceui.annotation.MicClickAction.ForceUnMute
                    ),
                    com.easemob.secnceui.bean.MicManagerBean(
                        context.getString(R.string.chatroom_unblock),
                        true,
                        com.easemob.secnceui.annotation.MicClickAction.UnLock
                    )
                )
            }
            // 空闲
            com.easemob.secnceui.annotation.MicStatus.Idle -> {
                mutableListOf(
                    com.easemob.secnceui.bean.MicManagerBean(
                        context.getString(R.string.chatroom_invite),
                        true,
                        com.easemob.secnceui.annotation.MicClickAction.Invite
                    ),
                    com.easemob.secnceui.bean.MicManagerBean(
                        context.getString(R.string.chatroom_mute),
                        true,
                        com.easemob.secnceui.annotation.MicClickAction.ForceMute
                    ),
                    com.easemob.secnceui.bean.MicManagerBean(
                        context.getString(R.string.chatroom_block),
                        true,
                        com.easemob.secnceui.annotation.MicClickAction.Lock
                    )
                )
            }
            else -> mutableListOf()
        }
    }

    /**
     * 嘉宾点击麦位管理
     */
    fun builderGuestMicMangerList(context: Context, micInfo: com.easemob.secnceui.bean.MicInfoBean): MutableList<com.easemob.secnceui.bean.MicManagerBean> {
        return when (micInfo.micStatus) {
            // 有⼈-正常
            com.easemob.secnceui.annotation.MicStatus.Normal -> {
                mutableListOf(
                    com.easemob.secnceui.bean.MicManagerBean(
                        context.getString(R.string.chatroom_mute),
                        true,
                        com.easemob.secnceui.annotation.MicClickAction.Mute
                    ),
                    com.easemob.secnceui.bean.MicManagerBean(
                        context.getString(R.string.chatroom_off_stage),
                        true,
                        com.easemob.secnceui.annotation.MicClickAction.OffStage
                    )
                )
            }
            // 有⼈-关麦
            com.easemob.secnceui.annotation.MicStatus.Mute -> {
                mutableListOf(
                    com.easemob.secnceui.bean.MicManagerBean(
                        context.getString(R.string.chatroom_unmute),
                        true,
                        com.easemob.secnceui.annotation.MicClickAction.UnMute
                    ),
                    com.easemob.secnceui.bean.MicManagerBean(
                        context.getString(R.string.chatroom_off_stage),
                        true,
                        com.easemob.secnceui.annotation.MicClickAction.OffStage
                    )
                )
            }
            // 有⼈-禁麦（被房主强制静音）
            com.easemob.secnceui.annotation.MicStatus.ForceMute -> {
                mutableListOf(
                    com.easemob.secnceui.bean.MicManagerBean(
                        context.getString(R.string.chatroom_unmute),
                        false,
                        com.easemob.secnceui.annotation.MicClickAction.ForceUnMute
                    ),
                    com.easemob.secnceui.bean.MicManagerBean(
                        context.getString(R.string.chatroom_off_stage),
                        true,
                        com.easemob.secnceui.annotation.MicClickAction.OffStage
                    )
                )
            }
            // 其他情况 nothing
            else -> {
                mutableListOf()
            }

        }
    }
}