package io.agora.secnceui.annotation

import androidx.annotation.IntDef
import io.agora.config.ConfigConstants

/**
 * @author create by zhangwei03
 *
 * 语料名称类型
 */
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@IntDef(
    SoundAudioType.CreateCommonRoom,
    SoundAudioType.CreateSpatialRoom,
    SoundAudioType.SocialChat,
    SoundAudioType.Karaoke,
    SoundAudioType.GamingBuddy,
    SoundAudioType.ProfessionalBroadcaster,
    SoundAudioType.ANISSwitch
)
annotation class SoundAudioType {
    companion object {
        const val CreateCommonRoom = ConfigConstants.Audio_Create_Common_Room
        const val CreateSpatialRoom = ConfigConstants.Audio_Create_Spatial_Room
        const val SocialChat = ConfigConstants.Audio_Sound_Social_Chat
        const val Karaoke = ConfigConstants.Audio_Sound_Karaoke
        const val GamingBuddy = ConfigConstants.Audio_Sound_Gaming_Buddy
        const val ProfessionalBroadcaster = ConfigConstants.Audio_Sound_Professional_Broadcaster
        const val ANISSwitch = ConfigConstants.Audio_ANIS_Switch
    }
}
