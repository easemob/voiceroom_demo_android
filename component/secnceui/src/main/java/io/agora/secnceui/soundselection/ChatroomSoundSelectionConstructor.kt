package io.agora.secnceui.soundselection

import android.content.Context
import io.agora.secnceui.R
import io.agora.secnceui.annotation.SoundSelectionType
import io.agora.secnceui.bean.CustomerUsageBean
import io.agora.secnceui.bean.SoundSelectionBean

object ChatroomSoundSelectionConstructor {

    fun builderSpatialList(context: Context, @SoundSelectionType currentSelection: Int): MutableList<SoundSelectionBean> {

        val soundSelectionList = mutableListOf<SoundSelectionBean>()
        soundSelectionList.apply {
            add(
                SoundSelectionBean(
                    soundSelection = SoundSelectionType.SocialChat,
                    index = 0,
                    soundName = context.getString(R.string.chatroom_social_chat),
                    soundIntroduce = context.getString(R.string.chatroom_social_chat_introduce),
                    isCurrentUsing = currentSelection == SoundSelectionType.SocialChat,
                    customer = mutableListOf<CustomerUsageBean>().apply {
                        add(CustomerUsageBean("ya", R.drawable.icon_chatroom_ya_launcher))
                        add(CustomerUsageBean("soul", R.drawable.icon_chatroom_soul_launcher))
                    }
                )
            )
            add(
                SoundSelectionBean(
                    soundSelection = SoundSelectionType.Karaoke,
                    index = 1,
                    soundName = context.getString(R.string.chatroom_karaoke),
                    soundIntroduce = context.getString(R.string.chatroom_karaoke_introduce),
                    isCurrentUsing = currentSelection == SoundSelectionType.Karaoke,
                    customer = mutableListOf<CustomerUsageBean>().apply {
                        add(CustomerUsageBean("ya", R.drawable.icon_chatroom_ya_launcher))
                        add(CustomerUsageBean("soul", R.drawable.icon_chatroom_soul_launcher))
                    })
            )
            add(
                SoundSelectionBean(
                    soundSelection = SoundSelectionType.GamingBuddy,
                    index = 2,
                    soundName = context.getString(R.string.chatroom_gaming_buddy),
                    soundIntroduce = context.getString(R.string.chatroom_gaming_buddy_introduce),
                    isCurrentUsing = currentSelection == SoundSelectionType.GamingBuddy,
                    customer = mutableListOf<CustomerUsageBean>().apply {
                        add(CustomerUsageBean("ya", R.drawable.icon_chatroom_ya_launcher))
                        add(CustomerUsageBean("soul", R.drawable.icon_chatroom_soul_launcher))
                    })
            )
        }
        val comparator: Comparator<SoundSelectionBean> = Comparator { o1, o2 ->
            o2.isCurrentUsing.compareTo(o1.isCurrentUsing)
        }
        soundSelectionList.sortWith(comparator)
        return soundSelectionList
    }
}