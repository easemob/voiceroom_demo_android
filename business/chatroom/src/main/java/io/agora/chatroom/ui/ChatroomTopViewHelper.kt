package io.agora.chatroom.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import io.agora.secnceui.R
import io.agora.secnceui.annotation.AINSModeType
import io.agora.secnceui.annotation.SoundSelectionType
import io.agora.secnceui.bean.ChatroomAudioSettingsBean
import io.agora.secnceui.bean.SoundSelectionBean
import io.agora.secnceui.ui.ainoise.ChatroomAINSSheetDialog
import io.agora.secnceui.ui.audiosettings.ChatroomAudioSettingsSheetDialog
import io.agora.secnceui.ui.common.CommonFragmentAlertDialog
import io.agora.secnceui.ui.common.CommonSheetContentDialog
import io.agora.secnceui.ui.rank.ChatroomContributionAndAudienceSheetDialog
import io.agora.secnceui.ui.soundselection.ChatroomSocialChatSheetDialog
import io.agora.secnceui.ui.soundselection.ChatroomSoundSelectionConstructor
import io.agora.secnceui.ui.soundselection.ChatroomSoundSelectionSheetDialog
import io.agora.secnceui.ui.spatialaudio.ChatroomSpatialAudioSheetDialog
import io.agora.secnceui.widget.top.ChatroomLiveTopView
import io.agora.secnceui.widget.top.OnLiveTopClickListener

/**
 * @author create by zhangwei03
 */
object ChatroomTopViewHelper {

    fun createTopViewClickListener(
        activity: FragmentActivity,
        finishBack: () -> Unit,
        isOwner: Boolean = true
    ): OnLiveTopClickListener {
        val liveTopClickListener = object : OnLiveTopClickListener {
            override fun onClickView(view: View, action: Int) {
                when (action) {
                    ChatroomLiveTopView.ClickAction.BACK -> {
                        CommonFragmentAlertDialog()
                            .titleText(activity.getString(R.string.chatroom_prompt))
                            .contentText(activity.getString(R.string.chatroom_exit))
                            .leftText(activity.getString(R.string.chatroom_cancel))
                            .rightText(activity.getString(R.string.chatroom_confirm))
                            .setOnClickListener(object : CommonFragmentAlertDialog.OnClickBottomListener {
                                override fun onConfirmClick() {
                                    finishBack.invoke()
                                }
                            })
                            .show(activity.supportFragmentManager, "mtExitDialog")
                    }
                    ChatroomLiveTopView.ClickAction.RANK -> {
                        ChatroomContributionAndAudienceSheetDialog(activity).show(
                            activity.supportFragmentManager,
                            "ContributionAndAudienceSheetDialog"
                        )
                    }
                    ChatroomLiveTopView.ClickAction.NOTICE -> {
                        CommonSheetContentDialog()
                            .titleText(activity.getString(io.agora.secnceui.R.string.chatroom_notice))
                            .contentText(activity.getString(R.string.chatroom_first_enter_room_notice_tips))
                            .show(activity.supportFragmentManager, "mtContentSheet")

                    }
                    ChatroomLiveTopView.ClickAction.SOCIAL -> {
                        val curSoundSelection = ChatroomSoundSelectionConstructor.builderCurSoundSelection(
                            activity, SoundSelectionType.SocialChat
                        )
                        ChatroomSocialChatSheetDialog(object :
                            ChatroomSocialChatSheetDialog.OnClickSocialChatListener {

                            override fun onMoreSound() {
                                ChatroomSpatialAudioSheetDialog(false).show(
                                    activity.supportFragmentManager, "mtSpatialAudio"
                                )
                            }
                        }).contentText(curSoundSelection.soundIntroduce)
                            .customers(curSoundSelection.customer ?: mutableListOf())
                            .show(activity.supportFragmentManager, "chatroomSocialChatSheetDialog")
                    }
                }
            }
        }
        return liveTopClickListener
    }

    fun showAudioSettingsDialog(activity: FragmentActivity, finishBack: () -> Unit, isOwner: Boolean) {
        ChatroomAudioSettingsSheetDialog(object :
            ChatroomAudioSettingsSheetDialog.OnClickAudioSettingsListener {

            override fun onSoundEffect(@SoundSelectionType soundSelection: Int, isEnable: Boolean) {
                ChatroomSoundSelectionSheetDialog(object :
                    ChatroomSoundSelectionSheetDialog.OnClickSoundSelectionListener {

                    override fun onSoundEffect(soundSelection: SoundSelectionBean) {
                        CommonFragmentAlertDialog()
                            .titleText(activity.getString(R.string.chatroom_prompt))
                            .contentText(activity.getString(R.string.chatroom_exit_and_create_one))
                            .leftText(activity.getString(R.string.chatroom_cancel))
                            .rightText(activity.getString(R.string.chatroom_confirm))
                            .setOnClickListener(object :
                                CommonFragmentAlertDialog.OnClickBottomListener {
                                override fun onConfirmClick() {
                                    finishBack.invoke()
                                }
                            })
                            .show(activity.supportFragmentManager, "mtCenterDialog")
                    }

                }, isEnable).apply {
                    arguments = Bundle().apply {
                        putInt(
                            ChatroomSoundSelectionSheetDialog.KEY_CURRENT_SELECTION,
                            SoundSelectionType.Karaoke
                        )
                    }
                }
                    .show(activity.supportFragmentManager, "mtSoundSelection")
            }

            override fun onNoiseSuppression(@AINSModeType ainsModeType: Int, isEnable: Boolean) {
                ChatroomAINSSheetDialog(isEnable).apply {
                    arguments = Bundle().apply {
                        putInt(ChatroomAINSSheetDialog.KEY_AINS_MODE, AINSModeType.High)
                    }
                }
                    .show(activity.supportFragmentManager, "mtAnis")
            }

            override fun onSpatialAudio(isOpen: Boolean, isEnable: Boolean) {
                ChatroomSpatialAudioSheetDialog(isEnable).apply {
                    arguments = Bundle().apply {
                        putBoolean(ChatroomSpatialAudioSheetDialog.KEY_SPATIAL_OPEN, isOpen)
                    }
                }
                    .show(activity.supportFragmentManager, "mtSpatialAudio")
            }

        }, isOwner).apply {
            arguments = Bundle().apply {
                //test
                val audioSettingsInfo = ChatroomAudioSettingsBean(
                    botOpen = false,
                    botVolume = 50,
                    soundSelection = SoundSelectionType.GamingBuddy,
                    anisMode = AINSModeType.Medium,
                    spatialOpen = false
                )
                putSerializable(
                    ChatroomAudioSettingsSheetDialog.KEY_AUDIO_SETTINGS_INFO,
                    audioSettingsInfo
                )
            }
        }.show(activity.supportFragmentManager, "mtAudioSettings")
    }
}