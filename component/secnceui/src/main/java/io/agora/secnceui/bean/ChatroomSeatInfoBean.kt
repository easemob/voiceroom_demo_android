package io.agora.secnceui.bean

import androidx.annotation.DrawableRes
import androidx.annotation.IntDef

/**
 * 语聊房麦位用户状态状态，只跟踪麦上用户状态
 */
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
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

/**
 * 语聊房麦位状态
 */
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@IntDef(
    ChatroomWheatSeatType.Idle,
    ChatroomWheatSeatType.Mute,
    ChatroomWheatSeatType.Lock,
    ChatroomWheatSeatType.LockMute,
    ChatroomWheatSeatType.Normal,
    ChatroomWheatSeatType.NormalMute,
    ChatroomWheatSeatType.Inactive
)
annotation class ChatroomWheatSeatType {
    companion object {
        /**
         *  空置-开放：邀请上⻨ 座位禁⻨ 关闭座位
         *
         *  语聊房默认状态，任何⼈可以移动⻨位或申请上⻨
         */
        const val Idle = 0

        /**
         * 空置-座位禁⻨：邀请上⻨ 座位开⻨ 关闭座位
         *
         * 房主针对空置位禁⻨
         * ⽤户本地强制是关⻨状态，点击开⻨则提示“当前座位已被房主禁⻨”
         */
        const val Mute = 1

        /**
         * 空置-座位关闭：座位禁⻨ 开启座位
         *
         * 房主针对空置位关闭座位
         * 点击此⻨位⽆法邀请，点击邀请上⻨提示“当前座位已被房主关闭”
         * 当前⻨位⽆法申请上⻨，普通⽤户点击提示“当前座位已被房主关闭”
         */
        const val Lock = 2

        /**
         * 空置-座位禁⻨&座位关闭：座位开⻨ 开启座位
         *
         * 房主对当前座位即禁⻨⼜关闭⻨
         * 点击此⻨位⽆法邀请，点击邀请上⻨提示“当前座位已被房主关闭”
         * 当前⻨位⽆法申请上⻨，普通⽤户点击提示“当前座位已被房主关闭”
         * 可以执⾏作为开⻨和开启座位
         */
        const val LockMute = 3

        /**
         * 有⼈-正常：强制下⻨ 座位禁⻨ 关闭座位
         *
         * 最正常的⽤户上⻨后状态
         * ⽤户可以⾃⼰开⻨或者闭⻨
         * 房主可以对⽤户执⾏三个操作
         */
        const val Normal = 4

        /**
         * 有⼈-禁麦：强制下⻨ 座位开⻨ 关闭座位
         * 当前⽤户被放逐禁⻨后的状态
         * ⽤户⽆法⾃⼰开⻨，相当于被禁⾔了，只有房主解除该座位的禁⻨后⽅可说话和操作 ⻨位
         */
        const val NormalMute = 5

        // 机器人待激活状态
        const val Inactive = 6
    }
}

@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
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

@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@IntDef(
    ChatroomSeatActionType.Invite,
    ChatroomSeatActionType.Mute,
    ChatroomSeatActionType.UnMute,
    ChatroomSeatActionType.Block,
    ChatroomSeatActionType.UnBlock,
    ChatroomSeatActionType.KickOff,
    ChatroomSeatActionType.OffStage,
)
annotation class ChatroomSeatActionType {
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

data class SeatInfoBean constructor(
    val index: Int = 0,
    val name: String? = null,
    val avatar: String = "",
    val userId: String? = null,
    @ChatroomWheatSeatType val wheatSeatType: Int = ChatroomWheatSeatType.Idle,
    @ChatroomWheatUserRole val userRole: Int = ChatroomWheatUserRole.None,
    @ChatroomWheatUserStatus val userStatus: Int = ChatroomWheatUserStatus.None,
    @DrawableRes val rotImage: Int = 0,
) : BaseChatroomBean

data class BotSeatInfoBean(
    val blueBot: SeatInfoBean,
    val redBot: SeatInfoBean
)

data class SeatManagerBean constructor(
    val name: String,
    var enable: Boolean = true,
    @ChatroomSeatActionType var seatActionType: Int = ChatroomSeatActionType.Invite
) : BaseChatroomBean