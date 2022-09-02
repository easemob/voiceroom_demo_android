package io.agora.secnceui.audiosettings

import android.content.Context
import io.agora.secnceui.R
import io.agora.secnceui.annotation.AINSModeType
import io.agora.secnceui.annotation.SoundSelectionType

object ChatroomAudioSettingsConstructor {

    fun getSoundEffectName(context: Context, @SoundSelectionType soundSelection: Int): String {
        return when(soundSelection){
            SoundSelectionType.SocialChat -> context.getString(R.string.chatroom_social_chat)
            SoundSelectionType.Karaoke -> context.getString(R.string.chatroom_karaoke)
            SoundSelectionType.GamingBuddy -> context.getString(R.string.chatroom_gaming_buddy)
            else ->{
                context.getString(R.string.chatroom_none)
            }
        }
    }

    fun getAINSName(context: Context, @AINSModeType ainsMode: Int): String {
        return when(ainsMode){
            AINSModeType.High -> context.getString(R.string.chatroom_high)
            AINSModeType.Medium -> context.getString(R.string.chatroom_medium)
            else ->{
                context.getString(R.string.chatroom_off)
            }
        }
    }
}