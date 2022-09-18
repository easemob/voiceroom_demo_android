package io.agora.secnceui.annotation

import androidx.annotation.IntDef

/**
 * 麦位管理点击事件
 */
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@IntDef(
    MicClickAction.Invite,
    MicClickAction.Mute,
    MicClickAction.UnMute,
    MicClickAction.Block,
    MicClickAction.UnBlock,
    MicClickAction.KickOff,
    MicClickAction.OffStage,
)
annotation class MicClickAction {
    companion object {
        // 邀请
        const val Invite = 0

        // 禁麦
        const val Mute = 1

        // 开麦
        const val UnMute = 2

        // 关闭座位
        const val Block = 3

        // 打开座位
        const val UnBlock = 4

        // 强制下麦（房主操作）
        const val KickOff = 5

        // 下麦（嘉宾操作）
        const val OffStage = 6
    }
}
