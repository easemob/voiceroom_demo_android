package io.agora.chatroom.ui

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.SeekBar
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import io.agora.baseui.adapter.OnItemClickListener
import io.agora.baseui.general.callback.OnResourceParseCallback
import io.agora.baseui.general.net.Resource
import io.agora.baseui.interfaces.IParserSource
import io.agora.buddy.tool.ThreadManager
import io.agora.buddy.tool.ToastTools
import io.agora.buddy.tool.logD
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
import io.agora.secnceui.ui.mic.IRoomMicView
import io.agora.secnceui.ui.micmanger.RoomMicManagerSheetDialog
import io.agora.chatroom.ui.dialog.RoomContributionAndAudienceSheetDialog
import io.agora.chatroom.ui.dialog.RoomNoticeSheetDialog
import io.agora.secnceui.ui.soundselection.RoomSocialChatSheetDialog
import io.agora.secnceui.ui.soundselection.RoomSoundSelectionConstructor
import io.agora.secnceui.ui.soundselection.RoomSoundSelectionSheetDialog
import io.agora.secnceui.ui.spatialaudio.RoomSpatialAudioSheetDialog
import io.agora.secnceui.widget.top.IRoomLiveTopView
import tools.ValueCallBack
import tools.bean.VRGiftBean
import tools.bean.VRoomInfoBean
import kotlin.random.Random

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

    /**麦位信息，index,rtcUid*/
    private val micMap = mutableMapOf<Int, Int>()

    /**当前用户位置*/
    private var myselfIndex: Int = -1

    fun isOnMic(): Boolean {
        return myselfIndex >= 0
    }

    init {
        // 更新公告
        roomViewModel.roomNoticeObservable.observe(activity) { response: Resource<Boolean> ->
            parseResource(response, object : OnResourceParseCallback<Boolean>() {
                override fun onSuccess(data: Boolean?) {
                    if (data != true) return
                    ToastTools.show(activity, activity.getString(R.string.chatroom_notice_posted))
                }

                override fun onError(code: Int, message: String?) {
                    ToastTools.show(activity, activity.getString(R.string.chatroom_notice_posted_error))
                }
            })
        }
        // 打开机器人
        roomViewModel.openBotObservable.observe(activity) { response: Resource<Boolean> ->
            parseResource(response, object : OnResourceParseCallback<Boolean>() {
                override fun onSuccess(data: Boolean?) {
                    if (data != true) return
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

            override fun onUserVolume(rtcUid: Int, volume: Int) {
                "onAudioVolumeIndication uid:${rtcUid},volume:${volume}".logD("onUserVolume")
                if (rtcUid == 0) {
                    // 自己
                    if (myselfIndex >= 0) {
                        iRoomMicView.updateVolume(myselfIndex, volume)
                    }
                } else {
                    val micIndex = findIndexByRtcUid(rtcUid)
                    if (micIndex >= 0) {
                        iRoomMicView.updateVolume(micIndex, volume)
                    }
                }
            }
        })
        // 关麦
        micViewModel.closeMicObservable().observe(activity) { response: Resource<Pair<Int, Boolean>> ->
            parseResource(response, object : OnResourceParseCallback<Pair<Int, Boolean>>() {
                override fun onSuccess(data: Pair<Int, Boolean>?) {
                    "close mic：$data".logE()
                    data?.let {
                        if (it.second) {
                            ToastTools.show(activity, "close mic:${it.first}")
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
                            // 用户下麦
                            ToastTools.show(activity, activity.getString(R.string.chatroom_mic_off_stage))
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
                            ToastTools.show(activity, activity.getString(R.string.chatroom_mic_muted))
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
                            ToastTools.show(activity, activity.getString(R.string.chatroom_mic_unmuted))
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
                            ToastTools.show(activity, activity.getString(R.string.chatroom_mic_kicked_off))
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
                            ToastTools.show(activity, activity.getString(R.string.chatroom_mic_blocked))
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
                            ToastTools.show(activity, activity.getString(R.string.chatroom_mic_unblocked))
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
     * 麦位index,rtcUid
     */
    fun onUpdateMicMap(updateMap: Map<Int, MicInfoBean>) {
        updateMap.forEach { (index, micInfo) ->
            val rtcUid = micInfo.userInfo?.rtcUid ?: -1
            if (rtcUid > 0) {
                micMap[index] = rtcUid
                // 当前用户在麦位上
                if (rtcUid == ProfileManager.getInstance().rtcUid()) {
                    myselfIndex = index
                }
            } else {
                val removeRtcUid = micMap.remove(index)
                // 当前用户从麦位移除
                if (removeRtcUid == ProfileManager.getInstance().rtcUid()) {
                    myselfIndex = -1
                }
            }
        }
        RtcRoomController.get().switchRole(myselfIndex >= 0)
    }

    private fun findIndexByRtcUid(rtcUid: Int): Int {
        micMap.entries.forEach {
            if (it.value == rtcUid) {
                return it.key
            }
        }
        return -1
    }

    /**
     * 详情
     */
    fun onRoomDetails(vRoomInfoBean: VRoomInfoBean) {
        val isUseBot = vRoomInfoBean.room?.isUse_robot ?: false
        RtcRoomController.get().isUseBot = isUseBot
        RtcRoomController.get().botVolume = vRoomInfoBean.room?.robot_volume ?: 50
        RtcRoomController.get().soundEffect =
            vRoomInfoBean.room?.soundSelection ?: ConfigConstants.SoundSelection.Social_Chat

        val ownerUid = vRoomInfoBean.room?.owner?.uid ?: ""
        vRoomInfoBean.room?.let { vRoomInfo ->
            iRoomTopView.onChatroomInfo(RoomInfoConstructor.serverRoomInfo2UiRoomInfo(vRoomInfo))
        }
        vRoomInfoBean.mic_info?.let { micList ->
            val micInfoList: List<MicInfoBean> =
                RoomInfoConstructor.convertMicUiBean(micList, roomKitBean.roomType, ownerUid)
            micInfoList.forEach { micInfo ->
                micInfo.userInfo?.let { userInfo ->
                    val rtcUid = userInfo.rtcUid
                    val micIndex = micInfo.index
                    if (rtcUid > 0) {
                        if (rtcUid == ProfileManager.getInstance().rtcUid()) {
                            myselfIndex = micIndex
                        }
                        micMap[micIndex] = rtcUid
                    }
                }
            }
            iRoomMicView.onInitMic(micInfoList, vRoomInfoBean.room?.isUse_robot ?: false)
        }
    }

    /**
     * 排行榜
     */
    fun onClickRank(currentItem: Int = 0) {
        RoomContributionAndAudienceSheetDialog(activity, roomKitBean, currentItem).show(
            activity.supportFragmentManager, "ContributionAndAudienceSheetDialog"
        )
    }

    /**
     * 公告
     */
    fun onClickNotice(announcement: String) {
        RoomNoticeSheetDialog(activity, roomKitBean, confirmCallback = { newNotice ->
            roomViewModel.updateRoomNotice(activity, roomKitBean.roomId, newNotice)
        })
            .contentText(announcement)
            .show(activity.supportFragmentManager, "roomNoticeSheetDialog")
    }

    /**
     * 音效
     */
    fun onClickSoundSocial(soundSelection: Int, finishBack: () -> Unit) {
        val curSoundSelection = RoomSoundSelectionConstructor.builderCurSoundSelection(activity, soundSelection)
        RoomSocialChatSheetDialog(object :
            RoomSocialChatSheetDialog.OnClickSocialChatListener {

            override fun onMoreSound() {
                onSoundSelectionDialog(RtcRoomController.get().soundEffect, finishBack)
            }
        }).titleText(curSoundSelection.soundName)
            .contentText(curSoundSelection.soundIntroduce)
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
                        onExitRoom(
                            activity.getString(R.string.chatroom_prompt),
                            activity.getString(R.string.chatroom_exit_and_create_one),
                            finishBack
                        )
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
                        RtcRoomController.get()
                            .playEffect(soundAudioBean.soundId, audioUrl, soundAudioBean.speakerType)
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
    fun onExitRoom(title: String, content: String, finishBack: () -> Unit) {
        CommonFragmentAlertDialog()
            .titleText(title)
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
                                            ToastTools.show(
                                                activity,
                                                activity.getString(R.string.chatroom_mic_exchange_mic_success),
                                            )
                                        }

                                        override fun onError(code: Int, desc: String) {
                                            ToastTools.show(
                                                activity,
                                                activity.getString(R.string.chatroom_mic_exchange_mic_failed, desc),
                                            )
                                        }
                                    })
                        }
                    })
            else
                onRoomViewDelegateListener?.onUserClickOnStage(micInfo.index)
        } else if (roomKitBean.isOwner || ProfileManager.getInstance()
                .isMyself(micInfo.userInfo?.userId)
        ) { // 房主或者自己
            RoomMicManagerSheetDialog(object : OnItemClickListener<MicManagerBean> {
                override fun onItemClick(data: MicManagerBean, view: View, position: Int, viewType: Long) {
                    when (data.micClickAction) {
                        MicClickAction.Invite -> {
                            // 房主邀请他人
                            if (data.enable) {
                                onRoomViewDelegateListener?.onInvitation()
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
        val micIndex = if (index < 0) myselfIndex else index
        if (mute) {
            micViewModel.closeMic(activity, roomKitBean.roomId, micIndex)
        } else {
            micViewModel.cancelCloseMic(activity, roomKitBean.roomId, micIndex)
        }
    }

    private var updateRankRunnable: Runnable? = null

    // 收到礼物消息
    fun receiveGift(roomId: String) {
        if (updateRankRunnable != null) {
            ThreadManager.getInstance().removeCallbacks(updateRankRunnable)
        }
        val longDelay = Random.nextInt(1000, 10000)
        "receiveGift longDelay：$longDelay".logE(TAG)
        updateRankRunnable = Runnable {
            HttpManager.getInstance(activity).getGiftList(roomId, object : ValueCallBack<VRGiftBean> {
                override fun onSuccess(var1: VRGiftBean?) {
                    var1?.ranking_list?.let {
                        val rankList = RoomInfoConstructor.convertServerRankToUiRank(it)
                        if (activity.isFinishing) return
                        ThreadManager.getInstance().runOnMainThread {
                            iRoomTopView.onRankMember(rankList)
                        }
                    }
                }

                override fun onError(var1: Int, var2: String?) {

                }
            })
        }
        ThreadManager.getInstance().runOnMainThreadDelay(updateRankRunnable, longDelay)
    }

    fun receiveInviteSite(roomId: String, micIndex: Int) {
        CommonFragmentAlertDialog()
            .contentText(activity.getString(R.string.chatroom_mic_anchor_invited_you_on_stage))
            .leftText(activity.getString(R.string.chatroom_decline))
            .rightText(activity.getString(R.string.chatroom_accept))
            .setOnClickListener(object : CommonFragmentAlertDialog.OnClickBottomListener {
                override fun onConfirmClick() {
                    HttpManager.getInstance(activity)
                        .agreeMicInvitation(roomId, micIndex, object : ValueCallBack<Boolean> {
                            override fun onSuccess(var1: Boolean?) {

                            }

                            override fun onError(var1: Int, var2: String?) {

                            }
                        })
                }

                override fun onCancelClick() {
                    HttpManager.getInstance(activity).rejectMicInvitation(roomId, object : ValueCallBack<Boolean> {
                        override fun onSuccess(var1: Boolean?) {

                        }

                        override fun onError(var1: Int, var2: String?) {
                        }

                    })
                }
            })
            .show(activity.supportFragmentManager, "CommonFragmentAlertDialog")
    }

    fun onAddOrSubMemberCount(add: Boolean) {
        ThreadManager.getInstance().runOnMainThread {
            iRoomTopView.addOrSubMemberCount(add)
        }
    }

    var onRoomViewDelegateListener: OnRoomViewDelegateListener? = null

    interface OnRoomViewDelegateListener {

        fun onInvitation()

        // 用户点击上台
        fun onUserClickOnStage(micIndex: Int)
    }
}