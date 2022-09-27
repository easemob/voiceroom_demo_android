package io.agora.secnceui.annotation

import androidx.annotation.IntDef
import io.agora.config.ConfigConstants

@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@IntDef(
    SoundSelectionType.SocialChat,
    SoundSelectionType.Karaoke,
    SoundSelectionType.GamingBuddy,
    SoundSelectionType.ProfessionalBroadcaster,
)
annotation class SoundSelectionType {
    companion object {
        const val SocialChat = ConfigConstants.Social_Chat

        const val Karaoke = ConfigConstants.Karaoke

        const val GamingBuddy =  ConfigConstants.Gaming_Buddy

        const val ProfessionalBroadcaster = ConfigConstants.Professional_Broadcaster
    }
}

