package io.agora.secnceui.annotation

import androidx.annotation.IntDef

/**
 * 降噪设置数据，自己，机器人小蓝，机器人小红
 */
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@IntDef(AINSUser.Yours, AINSUser.BlueBot, AINSUser.RedBot)
annotation class AINSUser {
    companion object {
        const val Yours = 0

        const val BlueBot = 1

        const val RedBot = 2
    }
}
