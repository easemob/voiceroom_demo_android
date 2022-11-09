package com.easemob.secnceui.ui.ainoise

import android.content.Context
import com.easemob.config.ConfigConstants
import com.easemob.secnceui.R
import com.easemob.secnceui.bean.AINSModeBean
import com.easemob.secnceui.bean.AINSSoundsBean

object RoomAINSConstructor {

    /**
     * 降噪等级
     */
    fun builderDefaultAINSList(context: Context, anisMode: Int): MutableList<com.easemob.secnceui.bean.AINSModeBean> {
        return mutableListOf(
            AINSModeBean(
                context.getString(R.string.chatroom_your_ains),
                anisMode
            ),
//            AINSModeBean(context.getString(R.string.chatroom_agora_blue_bot_ains), AINSUser.BlueBot),
//            AINSModeBean(context.getString(R.string.chatroom_agora_red_bot_ains), AINSUser.RedBot)
        )
    }

    /**
     * 降噪音效
     */
    fun builderDefaultSoundList(context: Context): MutableList<com.easemob.secnceui.bean.AINSSoundsBean> {
        return mutableListOf(
            AINSSoundsBean(ConfigConstants.AINSSoundType.AINS_TVSound, context.getString(R.string.chatroom_sounds_tv)),
            AINSSoundsBean(
                ConfigConstants.AINSSoundType.AINS_KitchenSound,
                context.getString(R.string.chatroom_sounds_kitchen)
            ),
            AINSSoundsBean(
                ConfigConstants.AINSSoundType.AINS_StreetSound,
                context.getString(R.string.chatroom_sounds_street),
                context.getString(R.string.chatroom_sounds_street_tips)
            ),
            AINSSoundsBean(
                ConfigConstants.AINSSoundType.AINS_MachineSound,
                context.getString(R.string.chatroom_sounds_machine),
                context.getString(R.string.chatroom_sounds_machine_tips)
            ),
            AINSSoundsBean(
                ConfigConstants.AINSSoundType.AINS_OfficeSound,
                context.getString(R.string.chatroom_sounds_office),
                context.getString(R.string.chatroom_sounds_office_tips)
            ),
            AINSSoundsBean(
                ConfigConstants.AINSSoundType.AINS_HomeSound,
                context.getString(R.string.chatroom_sounds_home),
                context.getString(R.string.chatroom_sounds_home_tips)
            ),
            AINSSoundsBean(
                ConfigConstants.AINSSoundType.AINS_ConstructionSound,
                context.getString(R.string.chatroom_sounds_construction),
                context.getString(R.string.chatroom_sounds_construction_tips)
            ),
            AINSSoundsBean(
                ConfigConstants.AINSSoundType.AINS_AlertSound,
                context.getString(R.string.chatroom_sounds_alert)
            ),
            AINSSoundsBean(
                ConfigConstants.AINSSoundType.AINS_ApplauseSound,
                context.getString(R.string.chatroom_sounds_applause)
            ),
            AINSSoundsBean(
                ConfigConstants.AINSSoundType.AINS_WindSound,
                context.getString(R.string.chatroom_sounds_wind)
            ),
            AINSSoundsBean(
                ConfigConstants.AINSSoundType.AINS_MicPopFilterSound,
                context.getString(R.string.chatroom_sounds_mic_pop_filter)
            ),
            AINSSoundsBean(
                ConfigConstants.AINSSoundType.AINS_AudioFeedback,
                context.getString(R.string.chatroom_sounds_audio_feedback)
            ),
            AINSSoundsBean(
                ConfigConstants.AINSSoundType.AINS_MicrophoneFingerRub,
                context.getString(R.string.chatroom_sounds_microphone_finger_rub_sound)
            ),
            AINSSoundsBean(
                ConfigConstants.AINSSoundType.AINS_MicrophoneScreenTap,
                context.getString(R.string.chatroom_sounds_microphone_screen_tap_sound)
            )
        )
    }
}