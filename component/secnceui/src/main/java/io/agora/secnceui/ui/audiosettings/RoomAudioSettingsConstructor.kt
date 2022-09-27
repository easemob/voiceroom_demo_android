package io.agora.secnceui.ui.audiosettings

import android.content.Context
import io.agora.config.ConfigConstants
import io.agora.secnceui.R

object RoomAudioSettingsConstructor {

    fun getSoundEffectName(context: Context, soundSelectionType: Int): String {
        return when (soundSelectionType) {
            ConfigConstants.SoundSelection.Social_Chat -> context.getString(R.string.chatroom_social_chat)
            ConfigConstants.SoundSelection.Karaoke -> context.getString(R.string.chatroom_karaoke)
            ConfigConstants.SoundSelection.Gaming_Buddy -> context.getString(R.string.chatroom_gaming_buddy)
            ConfigConstants.SoundSelection.Professional_Broadcaster -> context.getString(R.string.chatroom_professional_broadcaster)
            else -> {
                context.getString(R.string.chatroom_none)
            }
        }
    }

    fun getAINSName(context: Context, ainsMode: Int): String {
        return when (ainsMode) {
            ConfigConstants.AINSMode.AINS_High -> context.getString(R.string.chatroom_high)
            ConfigConstants.AINSMode.AINS_Medium -> context.getString(R.string.chatroom_medium)
            else -> {
                context.getString(R.string.chatroom_off)
            }
        }
    }
}