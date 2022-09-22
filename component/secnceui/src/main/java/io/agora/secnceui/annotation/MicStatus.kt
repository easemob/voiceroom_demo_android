package io.agora.secnceui.annotation

import androidx.annotation.IntDef

/**
 * 语聊房麦位状态
 *
 * 0:正常状态 1:闭麦 2:禁言 3:锁麦 4:锁麦和禁言 -1:空闲 5:机器人专属激活状态 -2:机器人专属关闭状态
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.TYPE)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@IntDef(
    MicStatus.Idle,
    MicStatus.Normal,
    MicStatus.Mute,
    MicStatus.ForceMute,
    MicStatus.Close,
    MicStatus.CloseForceMute,
    MicStatus.BotInactive,
    MicStatus.BotActivated,
)
annotation class MicStatus {
    companion object {
        const val Idle = -1
        const val Normal = 0
        const val Mute = 1
        const val ForceMute = 2
        const val Close = 3
        const val CloseForceMute = 4

        // 机器人激活状态
        const val BotActivated = 5

        // 机器人待激活状态
        const val BotInactive = -2
    }
}
