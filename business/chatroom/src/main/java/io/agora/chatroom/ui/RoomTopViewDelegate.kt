package io.agora.chatroom.ui

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.FragmentActivity
import io.agora.chatroom.general.constructor.RoomInfoConstructor
import io.agora.secnceui.R
import io.agora.secnceui.annotation.AINSModeType
import io.agora.secnceui.annotation.ChatroomTopType
import io.agora.secnceui.annotation.SoundSelectionType
import io.agora.secnceui.bean.RoomAudioSettingsBean
import io.agora.secnceui.bean.SoundSelectionBean
import io.agora.secnceui.ui.ainoise.RoomAINSSheetDialog
import io.agora.secnceui.ui.audiosettings.RoomAudioSettingsSheetDialog
import io.agora.secnceui.ui.common.CommonFragmentAlertDialog
import io.agora.secnceui.ui.common.CommonSheetContentDialog
import io.agora.secnceui.ui.rank.RoomContributionAndAudienceSheetDialog
import io.agora.secnceui.ui.soundselection.RoomSocialChatSheetDialog
import io.agora.secnceui.ui.soundselection.RoomSoundSelectionConstructor
import io.agora.secnceui.ui.soundselection.RoomSoundSelectionSheetDialog
import io.agora.secnceui.ui.spatialaudio.RoomSpatialAudioSheetDialog
import io.agora.secnceui.widget.top.IChatroomLiveTopView
import tools.bean.VRoomInfoBean

/**
 * @author create by zhangwei03
 */
class RoomTopViewDelegate constructor(
    private val activity: FragmentActivity,
    private val topView: IChatroomLiveTopView
) {

    fun onRoomDetails(vRoomBean: VRoomInfoBean) {
        topView.onChatroomInfo(RoomInfoConstructor.serverRoomInfo2UiRoomInfo(vRoomBean))
    }

    fun onTextUpdate(@ChatroomTopType type: Int, text: String) {
        topView.onTextUpdate(type, text)
    }

    fun onImageUpdate(@ChatroomTopType type: Int, avatar: String) {
        topView.onImageUpdate(type, avatar)
    }

    /**
     * 返回
     */
    fun onClickBank(finishBack: () -> Unit) {
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

    /**
     * 排行榜
     */
    fun onClickRank(vRoomInfo: VRoomInfoBean) {
        RoomContributionAndAudienceSheetDialog(activity).show(
            activity.supportFragmentManager,
            "ContributionAndAudienceSheetDialog"
        )
    }

    /**
     * 公告
     */
    fun onClickNotice(vRoomInfo: VRoomInfoBean) {
        var notice = vRoomInfo.room?.announcement ?: ""
        if (TextUtils.isEmpty(notice)) {
            notice = activity.getString(R.string.chatroom_first_enter_room_notice_tips)
        }
        CommonSheetContentDialog()
            .titleText(activity.getString(R.string.chatroom_notice))
            .contentText(notice)
            .show(activity.supportFragmentManager, "mtContentSheet")
    }

    /**
     * 音效
     */
    fun onClickSoundSocial(vRoomInfo: VRoomInfoBean) {
        val curSoundSelection = RoomSoundSelectionConstructor.builderCurSoundSelection(
            activity, SoundSelectionType.SocialChat
        )
        RoomSocialChatSheetDialog(object :
            RoomSocialChatSheetDialog.OnClickSocialChatListener {

            override fun onMoreSound() {
                RoomSpatialAudioSheetDialog(false).show(
                    activity.supportFragmentManager, "mtSpatialAudio"
                )
            }
        }).contentText(curSoundSelection.soundIntroduce)
            .customers(curSoundSelection.customer ?: mutableListOf())
            .show(activity.supportFragmentManager, "chatroomSocialChatSheetDialog")
    }

    fun onAudioSettingsDialog(finishBack: () -> Unit, isOwner: Boolean) {
        RoomAudioSettingsSheetDialog(object :
            RoomAudioSettingsSheetDialog.OnClickAudioSettingsListener {

            override fun onSoundEffect(@SoundSelectionType soundSelection: Int, isEnable: Boolean) {
                RoomSoundSelectionSheetDialog(object :
                    RoomSoundSelectionSheetDialog.OnClickSoundSelectionListener {

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
                            RoomSoundSelectionSheetDialog.KEY_CURRENT_SELECTION,
                            SoundSelectionType.Karaoke
                        )
                    }
                }
                    .show(activity.supportFragmentManager, "mtSoundSelection")
            }

            override fun onNoiseSuppression(@AINSModeType ainsModeType: Int, isEnable: Boolean) {
                RoomAINSSheetDialog(isEnable).apply {
                    arguments = Bundle().apply {
                        putInt(RoomAINSSheetDialog.KEY_AINS_MODE, AINSModeType.High)
                    }
                }
                    .show(activity.supportFragmentManager, "mtAnis")
            }

            override fun onSpatialAudio(isOpen: Boolean, isEnable: Boolean) {
                RoomSpatialAudioSheetDialog(isEnable).apply {
                    arguments = Bundle().apply {
                        putBoolean(RoomSpatialAudioSheetDialog.KEY_SPATIAL_OPEN, isOpen)
                    }
                }
                    .show(activity.supportFragmentManager, "mtSpatialAudio")
            }

        }, isOwner).apply {
            arguments = Bundle().apply {
                //test
                val audioSettingsInfo = RoomAudioSettingsBean(
                    botOpen = false,
                    botVolume = 50,
                    soundSelection = SoundSelectionType.GamingBuddy,
                    anisMode = AINSModeType.Medium,
                    spatialOpen = false
                )
                putSerializable(
                    RoomAudioSettingsSheetDialog.KEY_AUDIO_SETTINGS_INFO,
                    audioSettingsInfo
                )
            }
        }.show(activity.supportFragmentManager, "mtAudioSettings")
    }
}