package io.agora.secnceui.ui.soundselection

import android.content.Context
import io.agora.config.ConfigConstants
import io.agora.secnceui.R
import io.agora.secnceui.bean.CustomerUsageBean
import io.agora.secnceui.bean.SoundSelectionBean

object RoomSoundSelectionConstructor {

    fun builderSoundSelectionList(
        context: Context,
        curSoundSelectionType: Int
    ): MutableList<SoundSelectionBean> {

        val soundSelectionList = mutableListOf(
            SoundSelectionBean(
                soundSelectionType = ConfigConstants.SoundSelection.Social_Chat,
                index = 0,
                soundName = context.getString(R.string.chatroom_social_chat),
                soundIntroduce = context.getString(R.string.chatroom_social_chat_introduce),
                isCurrentUsing = curSoundSelectionType == ConfigConstants.SoundSelection.Social_Chat,
                customer = mutableListOf(
                    CustomerUsageBean("ya", R.drawable.icon_chatroom_ya_launcher),
                    CustomerUsageBean("soul", R.drawable.icon_chatroom_soul_launcher),
                )
            ),
            SoundSelectionBean(
                soundSelectionType =  ConfigConstants.SoundSelection.Karaoke,
                index = 1,
                soundName = context.getString(R.string.chatroom_karaoke),
                soundIntroduce = context.getString(R.string.chatroom_karaoke_introduce),
                isCurrentUsing = curSoundSelectionType ==  ConfigConstants.SoundSelection.Karaoke,
                customer = mutableListOf(
                    CustomerUsageBean("ya", R.drawable.icon_chatroom_ya_launcher),
                    CustomerUsageBean("soul", R.drawable.icon_chatroom_soul_launcher),
                )
            ),
            SoundSelectionBean(
                soundSelectionType = ConfigConstants.SoundSelection.Gaming_Buddy,
                index = 2,
                soundName = context.getString(R.string.chatroom_gaming_buddy),
                soundIntroduce = context.getString(R.string.chatroom_gaming_buddy_introduce),
                isCurrentUsing = curSoundSelectionType == ConfigConstants.SoundSelection.Gaming_Buddy,
                customer = mutableListOf(
                    CustomerUsageBean("ya", R.drawable.icon_chatroom_ya_launcher),
                    CustomerUsageBean("soul", R.drawable.icon_chatroom_soul_launcher),
                )
            ),
            SoundSelectionBean(
                soundSelectionType = ConfigConstants.SoundSelection.Professional_Broadcaster,
                index = 2,
                soundName = context.getString(R.string.chatroom_professional_broadcaster),
                soundIntroduce = context.getString(R.string.chatroom_professional_broadcaster_introduce),
                isCurrentUsing = curSoundSelectionType == ConfigConstants.SoundSelection.Professional_Broadcaster,
                customer = mutableListOf(
                    CustomerUsageBean("ya", R.drawable.icon_chatroom_ya_launcher),
                    CustomerUsageBean("soul", R.drawable.icon_chatroom_soul_launcher),
                )
            )
        )
        val comparator: Comparator<SoundSelectionBean> = Comparator { o1, o2 ->
            o2.isCurrentUsing.compareTo(o1.isCurrentUsing)
        }
        soundSelectionList.sortWith(comparator)
        return soundSelectionList
    }

    fun builderCurSoundSelection(context: Context, soundSelectionType: Int): SoundSelectionBean {
        return SoundSelectionBean(
            soundSelectionType = soundSelectionType,
            index = 0,
            soundName = soundNameBySoundSelectionType(context, soundSelectionType),
            soundIntroduce = soundIntroduceBySoundSelectionType(context, soundSelectionType),
            isCurrentUsing = true,
            customer = mutableListOf(
                CustomerUsageBean("ya", R.drawable.icon_chatroom_ya_launcher),
                CustomerUsageBean("soul", R.drawable.icon_chatroom_soul_launcher)
            )
        )
    }

    private fun soundNameBySoundSelectionType(context: Context, soundSelectionType: Int): String {
        return when (soundSelectionType) {
            ConfigConstants.SoundSelection.Karaoke -> {
                context.getString(R.string.chatroom_karaoke)
            }
            ConfigConstants.SoundSelection.Gaming_Buddy -> {
                context.getString(R.string.chatroom_gaming_buddy)
            }
            ConfigConstants.SoundSelection.Professional_Broadcaster -> {
                context.getString(R.string.chatroom_professional_broadcaster)
            }
            else -> {
                context.getString(R.string.chatroom_social_chat)
            }
        }
    }

    private fun soundIntroduceBySoundSelectionType(context: Context, soundSelectionType: Int): String {
        return when (soundSelectionType) {
            ConfigConstants.SoundSelection.Karaoke -> {
                context.getString(R.string.chatroom_karaoke_introduce)
            }
            ConfigConstants.SoundSelection.Gaming_Buddy -> {
                context.getString(R.string.chatroom_gaming_buddy_introduce)
            }
            ConfigConstants.SoundSelection.Professional_Broadcaster -> {
                context.getString(R.string.chatroom_professional_broadcaster_introduce)
            }
            else -> {
                context.getString(R.string.chatroom_social_chat_introduce)
            }
        }
    }
}