package io.agora.secnceui.annotation

import androidx.annotation.IntDef

/**
 * 语聊房用户角色
 */
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@IntDef(
    WheatUserRole.None,
    WheatUserRole.Owner,
    WheatUserRole.Guest,
    WheatUserRole.Audience,
    WheatUserRole.Robot
)
annotation class WheatUserRole {
    companion object {
        const val None = 0

        // 房主
        const val Owner = 1

        // 嘉宾
        const val Guest = 2

        // 观众
        const val Audience = 3

        // 机器人
        const val Robot = 4
    }
}