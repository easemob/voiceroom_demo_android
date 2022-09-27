package io.agora.secnceui.annotation

import androidx.annotation.IntDef
import io.agora.config.ConfigConstants

/**
 * @author create by zhangwei03
 *
 * 机器人播放类型
 */
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@IntDef(
    SoundSpeakerType.BotBlue,
    SoundSpeakerType.BotRed,
    SoundSpeakerType.BotBoth,
)
annotation class SoundSpeakerType {
    companion object {
        const val BotBlue = ConfigConstants.Speaker_Bot_Blue
        const val BotRed = ConfigConstants.Speaker_Bot_Red
        const val BotBoth = ConfigConstants.Speaker_Bot_Both
    }
}
