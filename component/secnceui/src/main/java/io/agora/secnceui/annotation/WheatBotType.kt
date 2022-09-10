package io.agora.secnceui.annotation

import androidx.annotation.IntDef

/**
 * @author create by zhangwei03
 * 语聊房机器人
 */
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@IntDef(WheatBotType.Blue, WheatBotType.Red)
annotation class WheatBotType {
    companion object {
        // 机器人小蓝
        const val Blue = 0

        // 机器人小红
        const val Red = 1
    }
}