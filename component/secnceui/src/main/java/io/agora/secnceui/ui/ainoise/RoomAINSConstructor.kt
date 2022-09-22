package io.agora.secnceui.ui.ainoise

import android.content.Context
import io.agora.secnceui.R
import io.agora.secnceui.annotation.AINSModeType
import io.agora.secnceui.bean.AINSModeBean
import io.agora.secnceui.bean.AINSSoundsBean

object RoomAINSConstructor {

    /**
     * 降噪等级
     */
    fun builderDefaultAINSList(context: Context, @AINSModeType anisMode: Int): MutableList<AINSModeBean> {
        return mutableListOf(
            AINSModeBean(context.getString(R.string.chatroom_your_ains), anisMode),
//            AINSModeBean(context.getString(R.string.chatroom_agora_blue_bot_ains), AINSUser.BlueBot),
//            AINSModeBean(context.getString(R.string.chatroom_agora_red_bot_ains), AINSUser.RedBot)
        )
    }

    /**
     * 降噪音效
     */
    fun builderDefaultSoundList(context: Context): MutableList<AINSSoundsBean> {
        return mutableListOf(
            AINSSoundsBean(context.getString(R.string.chatroom_sounds_tv)),
            AINSSoundsBean(context.getString(R.string.chatroom_sounds_kitchen)),
            AINSSoundsBean(
                context.getString(R.string.chatroom_sounds_street),
                context.getString(R.string.chatroom_sounds_street_tips)
            ),
            AINSSoundsBean(
                context.getString(R.string.chatroom_sounds_machine),
                context.getString(R.string.chatroom_sounds_machine_tips)
            ),
            AINSSoundsBean(
                context.getString(R.string.chatroom_sounds_office),
                context.getString(R.string.chatroom_sounds_office_tips)
            ),
            AINSSoundsBean(
                context.getString(R.string.chatroom_sounds_home),
                context.getString(R.string.chatroom_sounds_home_tips)
            ),
            AINSSoundsBean(
                context.getString(R.string.chatroom_sounds_construction),
                context.getString(R.string.chatroom_sounds_construction_tips)
            ),
            AINSSoundsBean(context.getString(R.string.chatroom_sounds_alert)),
            AINSSoundsBean(context.getString(R.string.chatroom_sounds_applause)),
            AINSSoundsBean(context.getString(R.string.chatroom_sounds_wind)),
            AINSSoundsBean(context.getString(R.string.chatroom_sounds_mic_pop_filter)),
            AINSSoundsBean(context.getString(R.string.chatroom_sounds_audio_feedback)),
            AINSSoundsBean(context.getString(R.string.chatroom_sounds_microphone_finger_rub_sound)),
            AINSSoundsBean(context.getString(R.string.chatroom_sounds_microphone_screen_tap_sound))
        )
    }
}