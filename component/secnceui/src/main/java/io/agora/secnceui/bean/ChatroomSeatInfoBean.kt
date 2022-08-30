package io.agora.secnceui.bean

import androidx.annotation.DrawableRes
import androidx.annotation.IntDef

/**
 * 语聊房麦位状态，只跟踪座位状态
 */
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@IntDef(
    ChatroomWheatSeatType.Idle,
    ChatroomWheatSeatType.Normal,
    ChatroomWheatSeatType.Mute,
    ChatroomWheatSeatType.Lock,
    ChatroomWheatSeatType.Inactive
)
annotation class ChatroomWheatSeatType {
    companion object {
        // 空闲
        const val Idle = 0

        // 有人在麦位
        const val Normal = 1

        // 静音
        const val Mute = 2

        // 锁麦
        const val Lock = 3

        // 机器人待激活状态
        const val Inactive = 4
    }
}

/**
 * 语聊房麦位用户状态状态，只跟踪麦上用户状态
 */
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@IntDef(
    ChatroomWheatUserStatus.None,
    ChatroomWheatUserStatus.Idle,
    ChatroomWheatUserStatus.Speaking,
    ChatroomWheatUserStatus.Mute
)
annotation class ChatroomWheatUserStatus {
    companion object {

        const val None = 0

        // 空闲
        const val Idle = 1

        // 正在说话
        const val Speaking = 2

        // 静音
        const val Mute = 3
    }
}

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@IntDef(
    ChatroomWheatUserRole.None,
    ChatroomWheatUserRole.Owner,
    ChatroomWheatUserRole.Guest,
    ChatroomWheatUserRole.Audience,
    ChatroomWheatUserRole.Robot
)
annotation class ChatroomWheatUserRole {
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

data class SeatInfoBean(
    val index: Int = 0,
    val name: String? = null,
    val avatar: String? = null,
    @ChatroomWheatSeatType val wheatSeatType: Int = ChatroomWheatSeatType.Idle,
    @ChatroomWheatUserRole val userRole: Int = ChatroomWheatUserRole.None,
    @ChatroomWheatUserStatus val userStatus: Int = ChatroomWheatUserStatus.None,
    @DrawableRes val rotImage: Int = 0,
) : BaseChatroomBean

data class BotSeatInfoBean(
    val blueBot:SeatInfoBean,
    val redBot:SeatInfoBean
)

data class SeatManagerBean(
    val name: String,

) : BaseChatroomBean