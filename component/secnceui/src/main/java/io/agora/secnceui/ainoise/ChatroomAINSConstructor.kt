package io.agora.secnceui.ainoise

import android.content.Context
import io.agora.secnceui.R
import io.agora.secnceui.annotation.AINSModeType
import io.agora.secnceui.bean.AINSModeBean
import io.agora.secnceui.bean.AINSSoundsBean

object ChatroomAINSConstructor {

    /**
     * 降噪等级
     */
    fun builderDefaultAINSList(context: Context,@AINSModeType anisType:Int): MutableList<AINSModeBean> {
        return mutableListOf<AINSModeBean>().apply {
            add(AINSModeBean(context.getString(R.string.chatroom_your_ains), anisType))
            add(AINSModeBean(context.getString(R.string.chatroom_agora_blue_bot_ains), AINSModeType.Off))
            add(AINSModeBean(context.getString(R.string.chatroom_agora_red_bot_ains), AINSModeType.Off))
        }
    }

    /**
     * 降噪音效
     */
    fun builderDefaultSoundList(context: Context): MutableList<AINSSoundsBean> {
        return mutableListOf<AINSSoundsBean>().apply {
            add(AINSSoundsBean(context.getString(R.string.chatroom_sounds_tv)))
            add(AINSSoundsBean(context.getString(R.string.chatroom_sounds_kitchen)))
            add(
                AINSSoundsBean(
                    context.getString(R.string.chatroom_sounds_street),
                    context.getString(R.string.chatroom_sounds_street_tips)
                )
            )
            add(
                AINSSoundsBean(
                    context.getString(R.string.chatroom_sounds_machine),
                    context.getString(R.string.chatroom_sounds_machine_tips)
                )
            )
            add(
                AINSSoundsBean(
                    context.getString(R.string.chatroom_sounds_office),
                    context.getString(R.string.chatroom_sounds_office_tips)
                )
            )
            add(
                AINSSoundsBean(
                    context.getString(R.string.chatroom_sounds_home),
                    context.getString(R.string.chatroom_sounds_home_tips)
                )
            )
            add(
                AINSSoundsBean(
                    context.getString(R.string.chatroom_sounds_construction),
                    context.getString(R.string.chatroom_sounds_construction_tips)
                )
            )
            add(AINSSoundsBean(context.getString(R.string.chatroom_sounds_alert)))
            add(AINSSoundsBean(context.getString(R.string.chatroom_sounds_applause)))
            add(AINSSoundsBean(context.getString(R.string.chatroom_sounds_wind)))
            add(AINSSoundsBean(context.getString(R.string.chatroom_sounds_mic_pop_filter)))
            add(AINSSoundsBean(context.getString(R.string.chatroom_sounds_audio_feedback)))
            add(AINSSoundsBean(context.getString(R.string.chatroom_sounds_microphone_finger_rub_sound)))
            add(AINSSoundsBean(context.getString(R.string.chatroom_sounds_microphone_screen_tap_sound)))
        }
    }
}