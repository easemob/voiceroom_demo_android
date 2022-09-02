package io.agora.secnceui.annotation

import androidx.annotation.IntDef

/**
 * 语聊房麦位状态
 */
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@IntDef(
    WheatSeatType.Idle,
    WheatSeatType.Mute,
    WheatSeatType.Lock,
    WheatSeatType.LockMute,
    WheatSeatType.Normal,
    WheatSeatType.NormalMute,
    WheatSeatType.Inactive
)
annotation class WheatSeatType {
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
