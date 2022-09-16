package io.agora.secnceui.annotation

import androidx.annotation.IntDef

/**
 * 语聊房麦位用户状态状态，只跟踪麦上用户状态
 */
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@IntDef(
    WheatUserStatus.None,
    WheatUserStatus.Idle,
    WheatUserStatus.Speaking,
    WheatUserStatus.Mute,
    WheatUserStatus.ForceMute
)
annotation class WheatUserStatus {

    companion object {

        const val None = 0

        // 空闲
        const val Idle = 1

        // 正在说话
        const val Speaking = 2

        // 静音
        const val Mute = 3

        // 被强制静音
        const val ForceMute = 4
    }
}

