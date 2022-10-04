package io.agora.chatroom.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.CompoundButton
import android.widget.SeekBar
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import io.agora.baseui.adapter.OnItemClickListener
import io.agora.baseui.general.callback.OnResourceParseCallback
import io.agora.baseui.general.net.Resource
import io.agora.baseui.interfaces.IParserSource
import io.agora.buddy.tool.ToastTools
import io.agora.buddy.tool.logE
import io.agora.chatroom.R
import io.agora.chatroom.bean.RoomKitBean
import io.agora.chatroom.controller.RtcMicVolumeListener
import io.agora.chatroom.controller.RtcRoomController
import io.agora.chatroom.general.constructor.RoomInfoConstructor
import io.agora.chatroom.general.net.HttpManager
import io.agora.chatroom.general.repositories.ProfileManager
import io.agora.chatroom.model.ChatroomViewModel
import io.agora.chatroom.model.RoomMicViewModel
import io.agora.config.ConfigConstants
import io.agora.secnceui.annotation.MicClickAction
import io.agora.secnceui.annotation.MicStatus
import io.agora.secnceui.bean.*
import io.agora.secnceui.ui.ainoise.RoomAINSSheetDialog
import io.agora.secnceui.ui.audiosettings.RoomAudioSettingsSheetDialog
import io.agora.secnceui.ui.common.CommonFragmentAlertDialog
import io.agora.secnceui.ui.common.CommonSheetAlertDialog
import io.agora.secnceui.ui.common.CommonSheetContentDialog
import io.agora.secnceui.ui.mic.IRoomMicView
import io.agora.secnceui.ui.micmanger.RoomMicManagerSheetDialog
import io.agora.chatroom.fragment.RoomContributionAndAudienceSheetDialog
import io.agora.secnceui.ui.soundselection.RoomSocialChatSheetDialog
import io.agora.secnceui.ui.soundselection.RoomSoundSelectionConstructor
import io.agora.secnceui.ui.soundselection.RoomSoundSelectionSheetDialog
import io.agora.secnceui.ui.spatialaudio.RoomSpatialAudioSheetDialog
import io.agora.secnceui.widget.top.IRoomLiveTopView
import tools.ValueCallBack
import tools.bean.VRoomInfoBean

/**
 * @author create by zhangwei03
 *
 * 房间头部 && 麦位置数据变化代理
 */
class RoomObservableViewDelegate constructor(
    private val activity: FragmentActivity,
    private val roomKitBean: RoomKitBean,
    private val iRoomTopView: IRoomLiveTopView, // 头部
    private val iRoomMicView: IRoomMicView, // 麦位
) : IParserSource {
    companion object {
        private const val TAG = "RoomObservableDelegate"
    }

    /**
     * room viewModel
     */
    private val roomViewModel: ChatroomViewModel by lazy {
        ViewModelProvider(activity)[ChatroomViewModel::class.java]
    }

    /**
     * mic viewModel
     */
    private val micViewModel: RoomMicViewModel by lazy {
        ViewModelProvider(activity)[RoomMicViewModel::class.java]
    }


    init {
        // 房间详情
        roomViewModel.roomDetailObservable.observe(activity) { response: Resource<VRoomInfoBean> ->
            parseResource(response, object : OnResourceParseCallback<VRoomInfoBean>() {

                override fun onSuccess(data: VRoomInfoBean?) {
                    "getRoomDetails onSuccess：$data".logE(TAG)
                    onRoomDetails(data)
                }
            })
        }
        // 打开机器人
        roomViewModel.openBotObservable.observe(activity) { response: Resource<Boolean> ->
            parseResource(response, object : OnResourceParseCallback<Boolean>() {
                override fun onSuccess(data: Boolean?) {
                    ToastTools.show(activity, "open bot onSuccess：$data")
                    if (data != true) return
                    iRoomMicView.activeBot(true)
                    // 创建房间，第⼀次启动机器⼈后播放音效：
                    if (RtcRoomController.get().firstActiveBot) {
                        RtcRoomController.get().firstActiveBot = false
                        RoomSoundAudioConstructor.createRoomSoundAudioMap[roomKitBean.roomType]?.let {
                            RtcRoomController.get().playEffect(it)
                        }
                    }
                }
            })
        }
        // 关闭机器人
        roomViewModel.closeBotObservable.observe(activity) { response: Resource<Boolean> ->
            parseResource(response, object : OnResourceParseCallback<Boolean>() {
                override fun onSuccess(data: Boolean?) {
                    ToastTools.show(activity, "close bot onSuccess：$data")
                    if (data != true) return
                    // 关闭机器人，暂停所有音效播放
                    iRoomMicView.activeBot(false)
                    RtcRoomController.get().stopAllEffect()
                }
            })
        }
        // 机器人音量
        roomViewModel.robotVolumeObservable.observe(activity) { response: Resource<Boolean> ->
            parseResource(response, object : OnResourceParseCallback<Boolean>() {
                override fun onSuccess(data: Boolean?) {
                    if (data != true) return
                    ToastTools.show(activity, "change robot volume：$data")
                }
            })
        }
        // 麦位音量监听
        RtcRoomController.get().setMicVolumeListener(object : RtcMicVolumeListener() {
            // 更新机器人音量
            override fun onBotVolume(speaker: Int, finished: Boolean) {
                if (finished) {
                    iRoomMicView.updateBotVolume(speaker, ConfigConstants.VolumeType.Volume_None)
                } else {
                    iRoomMicView.updateBotVolume(speaker, ConfigConstants.VolumeType.Volume_Medium)
                }
            }
        })
        // 撤销上麦申请监听
        micViewModel.cancelSubmitMicObservable().observe(activity) { response: Resource<Boolean> ->
            parseResource(response, object : OnResourceParseCallback<Boolean>() {
                override fun onSuccess(data: Boolean?) {
                    "cancel submit mic：$data".logE()
                    if (data != true) return
                    ToastTools.show(activity, "cancel submit mic:$data")
                }
            })
        }
        // 关麦
        micViewModel.closeMicObservable().observe(activity) { response: Resource<Pair<Int, Boolean>> ->
            parseResource(response, object : OnResourceParseCallback<Pair<Int, Boolean>>() {
                override fun onSuccess(data: Pair<Int, Boolean>?) {
                    "close mic：$data".logE()
                    data?.let {
                        if (it.second) {
                            ToastTools.show(activity, "close mic:${it.first}")
                            iRoomMicView.updateMicStatusByAction(it.first, MicClickAction.Mute)
                        }
                    }
                }
            })
        }
        // 取消关麦
        micViewModel.cancelCloseMicObservable().observe(activity) { response: Resource<Pair<Int, Boolean>> ->
            parseResource(response, object : OnResourceParseCallback<Pair<Int, Boolean>>() {
                override fun onSuccess(data: Pair<Int, Boolean>?) {
                    "cancel close mic：$data".logE()
                    data?.let {
                        if (it.second) {
                            ToastTools.show(activity, "cancel close mic:${it.first}")
                            iRoomMicView.updateMicStatusByAction(it.first, MicClickAction.UnMute)
                        }
                    }
                }
            })
        }
        // 下麦
        micViewModel.leaveMicObservable().observe(activity) { response: Resource<Pair<Int, Boolean>> ->
            parseResource(response, object : OnResourceParseCallback<Pair<Int, Boolean>>() {
                override fun onSuccess(data: Pair<Int, Boolean>?) {
                    "leave mic：$data".logE()
                    data?.let {
                        if (it.second) {
                            ToastTools.show(activity, "leave mic:${it.first}")
                            iRoomMicView.updateMicStatusByAction(it.first, MicClickAction.OffStage)
                        }
                    }
                }
            })
        }
        // 禁言指定麦位
        micViewModel.muteMicObservable().observe(activity) { response: Resource<Pair<Int, Boolean>> ->
            parseResource(response, object : OnResourceParseCallback<Pair<Int, Boolean>>() {
                override fun onSuccess(data: Pair<Int, Boolean>?) {
                    "mute mic：$data".logE()
                    data?.let {
                        if (it.second) {
                            ToastTools.show(activity, "mute mic:${it.first}")
                            iRoomMicView.updateMicStatusByAction(it.first, MicClickAction.ForceMute)
                        }
                    }
                }
            })
        }
        // 取消禁言指定麦位
        micViewModel.cancelMuteMicObservable().observe(activity) { response: Resource<Pair<Int, Boolean>> ->
            parseResource(response, object : OnResourceParseCallback<Pair<Int, Boolean>>() {
                override fun onSuccess(data: Pair<Int, Boolean>?) {
                    "cancel mute mic：$data".logE()
                    data?.let {
                        if (it.second) {
                            ToastTools.show(activity, "cancel mute mic:${it.first}")
                            iRoomMicView.updateMicStatusByAction(it.first, MicClickAction.ForceUnMute)
                        }
                    }
                }
            })
        }
        // 踢用户下麦
        micViewModel.kickMicObservable().observe(activity) { response: Resource<Pair<Int, Boolean>> ->
            parseResource(response, object : OnResourceParseCallback<Pair<Int, Boolean>>() {
                override fun onSuccess(data: Pair<Int, Boolean>?) {
                    "kick mic：$data".logE()
                    data?.let {
                        if (it.second) {
                            ToastTools.show(activity, "kick mic:${it.first}")
                            iRoomMicView.updateMicStatusByAction(it.first, MicClickAction.KickOff)
                        }
                    }
                }
            })
        }
        // 用户拒绝申请上麦
        micViewModel.rejectMicInvitationObservable().observe(activity) { response: Resource<Boolean> ->
            parseResource(response, object : OnResourceParseCallback<Boolean>() {
                override fun onSuccess(data: Boolean?) {
                    "reject mic invitation：$data".logE()
                    if (data != true) return
                    ToastTools.show(activity, "reject mic invitation:$data")
                }
            })
        }
        // 锁麦
        micViewModel.lockMicObservable().observe(activity) { response: Resource<Pair<Int, Boolean>> ->
            parseResource(response, object : OnResourceParseCallback<Pair<Int, Boolean>>() {
                override fun onSuccess(data: Pair<Int, Boolean>?) {
                    "lock mic：$data".logE()
                    data?.let {
                        if (it.second) {
                            ToastTools.show(activity, "lock mic:${it.first}")
                            iRoomMicView.updateMicStatusByAction(it.first, MicClickAction.Lock)
                        }
                    }
                }
            })
        }
        // 取消锁麦
        micViewModel.cancelLockMicObservable().observe(activity) { response: Resource<Pair<Int, Boolean>> ->
            parseResource(response, object : OnResourceParseCallback<Pair<Int, Boolean>>() {
                override fun onSuccess(data: Pair<Int, Boolean>?) {
                    "cancel lock mic：$data".logE()
                    data?.let {
                        if (it.second) {
                            ToastTools.show(activity, "cancel lock mic:${it.first}")
                            iRoomMicView.updateMicStatusByAction(it.first, MicClickAction.UnLock)
                        }
                    }
                }
            })
        }
        // 邀请上麦
        micViewModel.invitationMicObservable().observe(activity) { response: Resource<Boolean> ->
            parseResource(response, object : OnResourceParseCallback<Boolean>() {
                override fun onSuccess(data: Boolean?) {
                    "invitation mic：$data".logE()
                    if (data != true) return
                    ToastTools.show(activity, "invitation mic:$data")
                }
            })
        }
        // 同意上麦申请
        micViewModel.applySubmitMicObservable().observe(activity) { response: Resource<Boolean> ->
            parseResource(response, object : OnResourceParseCallback<Boolean>() {
                override fun onSuccess(data: Boolean?) {
                    "apply submit mic：$data".logE()
                    if (data != true) return
                    ToastTools.show(activity, "apply submit mic:$data")
                }
            })
        }
        // 拒绝上麦申请
        micViewModel.rejectSubmitMicObservable().observe(activity) { response: Resource<Boolean> ->
            parseResource(response, object : OnResourceParseCallback<Boolean>() {
                override fun onSuccess(data: Boolean?) {
                    "reject submit mic：$data".logE()
                    if (data != true) return
                    ToastTools.show(activity, "reject submit mic:$data")
                }
            })
        }
    }

    /**
     * 详情
     */
    fun onRoomDetails(vRoomInfoBean: VRoomInfoBean?) {
        val isUseBot = vRoomInfoBean?.room?.isUse_robot ?: false
        RtcRoomController.get().isUseBot = isUseBot
        RtcRoomController.get().botVolume = vRoomInfoBean?.room?.robot_volume ?: 50
        RtcRoomController.get().soundEffect =
            vRoomInfoBean?.room?.soundSelection ?: ConfigConstants.SoundSelection.Social_Chat

        val ownerUid = vRoomInfoBean?.room?.owner?.uid ?: ""
        vRoomInfoBean?.let {
            it.room?.let { vRoomInfo ->
                iRoomTopView.onChatroomInfo(RoomInfoConstructor.serverRoomInfo2UiRoomInfo(vRoomInfo))
            }
            it.mic_info?.let { micList ->
                iRoomMicView.updateAdapter(
                    RoomInfoConstructor.convertMicUiBean(micList, ownerUid), vRoomInfoBean.room?.isUse_robot ?: false
                )
            }
        }

    }

    /**
     * 排行榜
     */
    fun onClickRank(currentItem: Int = 0) {
        RoomContributionAndAudienceSheetDialog(activity, roomKitBean, currentItem).show(
            activity.supportFragmentManager,
            "ContributionAndAudienceSheetDialog"
        )
    }

    /**
     * 公告
     */
    fun onClickNotice(vRoomInfo: VRoomInfoBean?) {
        var notice = vRoomInfo?.room?.announcement ?: ""
        if (TextUtils.isEmpty(notice)) {
            notice = activity.getString(io.agora.secnceui.R.string.chatroom_first_enter_room_notice_tips)
        }
        CommonSheetContentDialog()
            .titleText(activity.getString(io.agora.secnceui.R.string.chatroom_notice))
            .contentText(notice)
            .show(activity.supportFragmentManager, "mtContentSheet")
    }

    /**
     * 音效
     */
    fun onClickSoundSocial(vRoomInfo: VRoomInfoBean?) {
        val curSoundSelection = RoomSoundSelectionConstructor.builderCurSoundSelection(
            activity, ConfigConstants.SoundSelection.Social_Chat
        )
        RoomSocialChatSheetDialog(object :
            RoomSocialChatSheetDialog.OnClickSocialChatListener {

            override fun onMoreSound() {
                RoomSpatialAudioSheetDialog(roomKitBean.isOwner).show(
                    activity.supportFragmentManager, "mtSpatialAudio"
                )
            }
        }).contentText(curSoundSelection.soundIntroduce)
            .customers(curSoundSelection.customer ?: mutableListOf())
            .show(activity.supportFragmentManager, "chatroomSocialChatSheetDialog")
    }

    /**
     * 音效设置
     */
    fun onAudioSettingsDialog(finishBack: () -> Unit) {
        RoomAudioSettingsSheetDialog(object :
            RoomAudioSettingsSheetDialog.OnClickAudioSettingsListener {

            override fun onBotCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                roomViewModel.activeBot(activity, roomKitBean.roomId, isChecked)
            }

            override fun onBotVolumeChange(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                roomViewModel.updateBotVolume(activity, roomKitBean.roomId, progress)
            }

            override fun onSoundEffect(soundSelectionType: Int, isEnable: Boolean) {
                onSoundSelectionDialog(soundSelectionType, finishBack)
            }

            override fun onNoiseSuppression(ainsMode: Int, isEnable: Boolean) {
                onAINSDialog(ainsMode)
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

    /**
     * 最佳音效选择
     */
    fun onSoundSelectionDialog(soundSelection: Int, finishBack: () -> Unit) {
        RoomSoundSelectionSheetDialog(roomKitBean.isOwner,
            object : RoomSoundSelectionSheetDialog.OnClickSoundSelectionListener {
                override fun onSoundEffect(soundSelection: SoundSelectionBean, isCurrentUsing: Boolean) {
                    if (isCurrentUsing) {
                        // 试听音效需要开启机器人
                        if (RtcRoomController.get().isUseBot) {
                            RoomSoundAudioConstructor.soundSelectionAudioMap[soundSelection.soundSelectionType]?.let {
                                RtcRoomController.get().playEffect(it)
                            }
                        } else {
                            onBotMicClick(false, activity.getString(R.string.chatroom_open_bot_to_sound_effect))
                        }
                    } else {
                        onExitRoom(activity.getString(R.string.chatroom_exit_and_create_one), finishBack)
                    }
                }

            }).apply {
            arguments = Bundle().apply {
                putInt(RoomSoundSelectionSheetDialog.KEY_CURRENT_SELECTION, soundSelection)
            }
        }
            .show(activity.supportFragmentManager, "mtSoundSelection")
    }

    /**
     * AI降噪弹框
     */
    fun onAINSDialog(ainsMode: Int) {
        RoomAINSSheetDialog(roomKitBean.isOwner,
            anisModeCallback = {
                RtcRoomController.get().anisMode = it.anisMode
                RtcRoomController.get().deNoise(it)
                "onAINSDialog anisModeCallback：$it".logE(TAG)
                if (roomKitBean.isOwner && RtcRoomController.get().isUseBot && RtcRoomController.get().firstSwitchAnis) {
                    RtcRoomController.get().firstSwitchAnis = false

                    RoomSoundAudioConstructor.anisIntroduceAudioMap[it.anisMode]?.let { soundAudioList ->
                        RtcRoomController.get().playEffect(soundAudioList)
                    }
                }
            },
            anisSoundCallback = { ainsSoundBean ->
                "onAINSDialog anisSoundCallback：$ainsSoundBean".logE(TAG)
                if (RtcRoomController.get().isUseBot) {
                    RoomSoundAudioConstructor.AINSSoundMap[ainsSoundBean.soundType]?.let { soundAudioBean ->
                        val audioUrl =
                            if (ainsSoundBean.soundMode == ConfigConstants.AINSMode.AINS_High) soundAudioBean.audioUrlHigh else soundAudioBean.audioUrl
                        // 试听降噪音效
                        RtcRoomController.get().playEffect(soundAudioBean.soundId, audioUrl, soundAudioBean.speakerType)
                    }
                } else {
                    onBotMicClick(false, activity.getString(R.string.chatroom_open_bot_to_sound_effect))
                }
            }
        ).apply {
            arguments = Bundle().apply {
                putInt(RoomAINSSheetDialog.KEY_AINS_MODE, ainsMode)
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

    /**
     * 点击麦位
     */
    fun onUserMicClick(micInfo: MicInfoBean) {
        if (micInfo.micStatus == MicStatus.Idle && !roomKitBean.isOwner) {
            val mineMicIndex = iRoomMicView.findMicByUid(ProfileManager.getInstance().myUid())
            if (mineMicIndex > 0)
                showAlertDialog(activity.getString(R.string.chatroom_exchange_mic),
                    object : CommonSheetAlertDialog.OnClickBottomListener {
                        override fun onConfirmClick() {
                            HttpManager.getInstance(activity)
                                .exChangeMic(
                                    roomKitBean.roomId,
                                    mineMicIndex,
                                    micInfo.index,
                                    object : ValueCallBack<Boolean?> {
                                        override fun onSuccess(var1: Boolean?) {
                                            iRoomMicView.exchangeMic(mineMicIndex, micInfo.index)
                                        }

                                        override fun onError(code: Int, desc: String) {
                                            ToastTools.show(
                                                activity,
                                                activity.getString(R.string.chatroom_mic_apply_fail, desc),
                                            )
                                        }
                                    })
                        }
                    })
            else
                showAlertDialog(activity.getString(R.string.chatroom_request_speak),
                    object : CommonSheetAlertDialog.OnClickBottomListener {
                        override fun onConfirmClick() {
                            HttpManager.getInstance(activity)
                                .submitMic(roomKitBean.roomId, micInfo.index, object : ValueCallBack<Boolean?> {
                                    override fun onSuccess(var1: Boolean?) {
                                        // 已经发出申请
                                        onRoomViewDelegateListener?.onSubmitMicResponse()
                                        ToastTools.show(
                                            activity, activity.getString(R.string.chatroom_mic_submit_sent),
                                        )
                                    }

                                    override fun onError(code: Int, desc: String) {
                                        ToastTools.show(
                                            activity,
                                            activity.getString(R.string.chatroom_mic_apply_fail, desc),
                                        )
                                    }
                                })
                        }
                    })
        } else if (roomKitBean.isOwner || ProfileManager.getInstance().isMyself(micInfo.userInfo?.userId)) { // 房主或者自己
            RoomMicManagerSheetDialog(object : OnItemClickListener<MicManagerBean> {
                override fun onItemClick(data: MicManagerBean, view: View, position: Int, viewType: Long) {
                    when (data.micClickAction) {
                        MicClickAction.Invite -> {
                            // 房主邀请他人
                            if (data.enable) {
                                onClickRank(currentItem = 1)
                            } else {
                                ToastTools.show(activity, activity.getString(R.string.chatroom_mic_close_by_host))
                            }
                        }
                        MicClickAction.ForceMute -> {
                            // 房主禁言其他座位
                            micViewModel.muteMic(activity, roomKitBean.roomId, micInfo.index)
                        }
                        MicClickAction.ForceUnMute -> {
                            // 房主取消禁言其他座位
                            micViewModel.cancelMuteMic(activity, roomKitBean.roomId, micInfo.index)
                        }
                        MicClickAction.Mute -> {
                            //自己禁言
                            muteLocalAudio(true, micInfo.index)
                        }
                        MicClickAction.UnMute -> {
                            //取消自己禁言
                            muteLocalAudio(false, micInfo.index)
                        }
                        MicClickAction.Lock -> {
                            //房主锁麦
                            micViewModel.lockMic(activity, roomKitBean.roomId, micInfo.index)
                        }
                        MicClickAction.UnLock -> {
                            //房主取消锁麦
                            micViewModel.cancelLockMic(activity, roomKitBean.roomId, micInfo.index)
                        }
                        MicClickAction.KickOff -> {
                            //房主踢用户下台
                            micViewModel.kickMic(
                                activity, roomKitBean.roomId, micInfo.userInfo?.userId ?: "", micInfo.index
                            )
                        }
                        MicClickAction.OffStage -> {
                            //用户主动下台
                            micViewModel.leaveMicMic(activity, roomKitBean.roomId, micInfo.index)
                        }
                    }
                }
            }).apply {
                arguments = Bundle().apply {
                    putSerializable(RoomMicManagerSheetDialog.KEY_MIC_INFO, micInfo)
                    putSerializable(RoomMicManagerSheetDialog.KEY_IS_OWNER, roomKitBean.isOwner)
                    putSerializable(
                        RoomMicManagerSheetDialog.KEY_IS_MYSELF,
                        ProfileManager.getInstance().isMyself(micInfo.userInfo?.userId)
                    )
                }
            }.show(activity.supportFragmentManager, "RoomMicManagerSheetDialog")
        }
    }

    /**
     * 点击机器人
     */
    fun onBotMicClick(isUserBot: Boolean, content: String) {
        if (isUserBot) {
//            Toast.makeText(activity, "${data.userInfo?.username}", Toast.LENGTH_SHORT).show()
        } else {
            CommonFragmentAlertDialog()
                .titleText(activity.getString(R.string.chatroom_prompt))
                .contentText(content)
                .leftText(activity.getString(R.string.chatroom_cancel))
                .rightText(activity.getString(R.string.chatroom_confirm))
                .setOnClickListener(object : CommonFragmentAlertDialog.OnClickBottomListener {
                    override fun onConfirmClick() {
                        roomViewModel.activeBot(activity, roomKitBean.roomId, true)
                    }
                })
                .show(activity.supportFragmentManager, "botActivatedDialog")
        }
    }

    fun showAlertDialog(content: String, onClickListener: CommonSheetAlertDialog.OnClickBottomListener) {
        CommonSheetAlertDialog()
            .contentText(content)
            .rightText(activity.getString(R.string.chatroom_confirm))
            .leftText(activity.getString(R.string.chatroom_cancel))
            .setOnClickListener(onClickListener)
            .show(activity.supportFragmentManager, "CommonSheetAlertDialog")
    }

    /**
     * 自己关麦
     */
    fun muteLocalAudio(mute: Boolean, index: Int = -1) {
        RtcRoomController.get().enableLocalAudio(mute)
        if (mute) {
            micViewModel.closeMic(activity, roomKitBean.roomId, index)
        } else {
            micViewModel.cancelCloseMic(activity, roomKitBean.roomId, index)
        }
    }

    var onRoomViewDelegateListener: OnRoomViewDelegateListener? = null

    interface OnRoomViewDelegateListener {
        // 提交上麦请求成功回调
        fun onSubmitMicResponse()
    }
}