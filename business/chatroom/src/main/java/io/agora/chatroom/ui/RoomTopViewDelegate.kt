package io.agora.chatroom.ui

import android.os.Bundle
import android.text.TextUtils
import android.widget.CompoundButton
import android.widget.SeekBar
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import io.agora.baseui.general.callback.OnResourceParseCallback
import io.agora.baseui.general.net.Resource
import io.agora.baseui.interfaces.IParserSource
import io.agora.buddy.tool.logE
import io.agora.chatroom.bean.RoomKitBean
import io.agora.chatroom.controller.RtcRoomController
import io.agora.chatroom.general.constructor.RoomInfoConstructor
import io.agora.chatroom.model.ChatroomViewModel
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
import tools.bean.VRoomDetail
import tools.bean.VRoomInfoBean

/**
 * @author create by zhangwei03
 */
class RoomTopViewDelegate constructor(
    private val activity: FragmentActivity,
    private val roomKitBean: RoomKitBean,
    private val topView: IChatroomLiveTopView
) : IParserSource {

    companion object {
        private const val TAG = "RoomTopViewDelegate"
    }

    private val chatroomViewModel: ChatroomViewModel by lazy {
        ViewModelProvider(activity)[ChatroomViewModel::class.java]
    }

    init {
        chatroomViewModel.openBotObservable.observe(activity) { response: Resource<Boolean> ->
            parseResource(response, object : OnResourceParseCallback<Boolean>() {
                override fun onSuccess(data: Boolean?) {
                    "open bot onSuccess：$data".logE(TAG)
                }

                override fun onError(code: Int, message: String?) {
                    "open bot code:$code message:$message".logE(TAG)
                }
            })
        }
        chatroomViewModel.closeBotObservable.observe(activity) { response: Resource<Boolean> ->
            parseResource(response, object : OnResourceParseCallback<Boolean>() {
                override fun onSuccess(data: Boolean?) {
                    "close bot onSuccess：$data".logE(TAG)
                }

                override fun onError(code: Int, message: String?) {
                    "close bot code:$code message:$message".logE(TAG)
                }
            })
        }
    }

    fun onRoomDetails(vRoomBean: VRoomDetail) {
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
        onExitRoom(activity.getString(R.string.chatroom_exit), finishBack)
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

    fun onAudioSettingsDialog(finishBack: () -> Unit) {
        RoomAudioSettingsSheetDialog(object :
            RoomAudioSettingsSheetDialog.OnClickAudioSettingsListener {

            override fun onBotCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                chatroomViewModel.activeBot(activity, roomKitBean.roomId, isChecked)
            }

            override fun onBotVolumeChange(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                chatroomViewModel.updateBotVolume(activity, roomKitBean.roomId, progress)
            }

            override fun onSoundEffect(@SoundSelectionType soundSelection: Int, isEnable: Boolean) {
                onSoundSelectionDialog(soundSelection, finishBack)
            }

            override fun onNoiseSuppression(@AINSModeType ainsModeType: Int, isEnable: Boolean) {
                onAINSDialog(ainsModeType)
            }

            override fun onSpatialAudio(isOpen: Boolean, isEnable: Boolean) {
                onSpatialDialog()
            }

        }).apply {
            arguments = Bundle().apply {
                val audioSettingsInfo = RoomAudioSettingsBean(
                    enable = roomKitBean.isOwner,
                    roomType = roomKitBean.roomType,
                    botOpen = RtcRoomController.get().isUseBot,
                    botVolume = RtcRoomController.get().botVolume,
                    soundSelection = RtcRoomController.get().soundEffect,
                    anisMode = RtcRoomController.get().anisMode,
                    spatialOpen = false
                )
                putSerializable(RoomAudioSettingsSheetDialog.KEY_AUDIO_SETTINGS_INFO, audioSettingsInfo)
            }
        }.show(activity.supportFragmentManager, "mtAudioSettings")
    }

    fun onSoundSelectionDialog(@SoundSelectionType soundSelection: Int, finishBack: () -> Unit) {
        RoomSoundSelectionSheetDialog(object :
            RoomSoundSelectionSheetDialog.OnClickSoundSelectionListener {

            override fun onSoundEffect(soundSelection: SoundSelectionBean) {
                onExitRoom(activity.getString(R.string.chatroom_exit_and_create_one), finishBack)
            }

        }, roomKitBean.isOwner).apply {
            arguments = Bundle().apply {
                putInt(RoomSoundSelectionSheetDialog.KEY_CURRENT_SELECTION, soundSelection)
            }
        }
            .show(activity.supportFragmentManager, "mtSoundSelection")
    }

    /**
     * AI降噪弹框
     */
    fun onAINSDialog(@AINSModeType ainsModeType: Int) {
        RoomAINSSheetDialog(roomKitBean.isOwner,
            anisModeCallback = {
                RtcRoomController.get().anisMode = it.anisMode
                RtcRoomController.get().deNoise(it)
            },
            anisSoundCallback = {
                RtcRoomController.get().playEffect(it)
            }
        ).apply {
            arguments = Bundle().apply {
                putInt(RoomAINSSheetDialog.KEY_AINS_MODE, ainsModeType)
            }
        }
            .show(activity.supportFragmentManager, "mtAnis")
    }

    /**
     * 空间音频弹框
     */
    fun onSpatialDialog() {
        RoomSpatialAudioSheetDialog(roomKitBean.isOwner).apply {
            arguments = Bundle().apply {
                putBoolean(RoomSpatialAudioSheetDialog.KEY_SPATIAL_OPEN, false)
            }
        }
            .show(activity.supportFragmentManager, "mtSpatialAudio")
    }

    /**
     * 退出房间
     */
    fun onExitRoom(content: String, finishBack: () -> Unit) {
        CommonFragmentAlertDialog()
            .titleText(activity.getString(R.string.chatroom_prompt))
            .contentText(content)
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
}