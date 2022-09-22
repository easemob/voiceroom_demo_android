package io.agora.chatroom.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import io.agora.baseui.adapter.OnItemClickListener
import io.agora.baseui.general.callback.OnResourceParseCallback
import io.agora.baseui.general.net.Resource
import io.agora.baseui.interfaces.IParserSource
import io.agora.buddy.tool.logE
import io.agora.chatroom.bean.RoomKitBean
import io.agora.chatroom.general.constructor.RoomInfoConstructor
import io.agora.chatroom.general.repositories.ProfileManager
import io.agora.chatroom.model.ChatroomViewModel
import io.agora.chatroom.model.RoomMicViewModel
import io.agora.secnceui.R
import io.agora.secnceui.annotation.MicClickAction
import io.agora.secnceui.annotation.MicStatus
import io.agora.secnceui.bean.MicInfoBean
import io.agora.secnceui.bean.MicManagerBean
import io.agora.secnceui.ui.common.CommonFragmentAlertDialog
import io.agora.secnceui.ui.common.CommonSheetAlertDialog
import io.agora.secnceui.ui.mic.flat.IRoom2DMicView
import io.agora.secnceui.ui.micmanger.RoomMicManagerSheetDialog
import tools.bean.VRoomMicInfo

/**
 * @author create by zhangwei03
 */
class Room2DMicViewDelegate constructor(
    private val activity: FragmentActivity,
    private val roomKitBean: RoomKitBean,
    private val room2dMicView: IRoom2DMicView
) : IParserSource {

    companion object {
        private const val TAG = "Room2DMicViewDelegate"
    }

    private val roomAudioViewModel: RoomMicViewModel by lazy {
        ViewModelProvider(activity)[RoomMicViewModel::class.java]
    }

    private val chatroomViewModel: ChatroomViewModel by lazy {
        ViewModelProvider(activity)[ChatroomViewModel::class.java]
    }

    init {
        roomAudioViewModel.submitMicObservable().observe(activity) { response: Resource<Boolean> ->

        }
        chatroomViewModel.openBotObservable.observe(activity) { response: Resource<Boolean> ->
            parseResource(response, object : OnResourceParseCallback<Boolean>() {
                override fun onSuccess(data: Boolean?) {
                    "open bot onSuccess：$data".logE(TAG)
                    if (data == true) {
                        room2dMicView.activeBot(true)
                    }
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
                    if (data == true) {
                        room2dMicView.activeBot(false)
                    }
                }

                override fun onError(code: Int, message: String?) {
                    "close bot code:$code message:$message".logE(TAG)
                }

            })
        }
    }

    private fun isMineInfo(micInfo: MicInfoBean): Boolean {
        val currentUid = ProfileManager.getInstance().profile?.uid
        val micUid = micInfo.userInfo?.userId
        return !currentUid.isNullOrEmpty() && TextUtils.equals(currentUid, micUid)
    }

    fun onRoomDetails(vRoomMicInfoList: List<VRoomMicInfo>, isUseBot: Boolean) {
        val micInfoList = RoomInfoConstructor.convertMicUiBean(vRoomMicInfoList)
        room2dMicView.updateAdapter(micInfoList, isUseBot)
    }

    fun onUserMicClick(data: MicInfoBean, view: View, position: Int, viewType: Long) {
        if (data.micStatus == MicStatus.Idle && !roomKitBean.isOwner) {
            CommonSheetAlertDialog()
                .contentText(activity.getString(R.string.chatroom_request_speak))
                .rightText(activity.getString(R.string.chatroom_confirm))
                .leftText(activity.getString(R.string.chatroom_cancel))
                .setOnClickListener(object : CommonSheetAlertDialog.OnClickBottomListener {
                    override fun onConfirmClick() {
                        roomAudioViewModel.submitMic(activity, roomKitBean.roomId, position)
                    }

                    override fun onCancelClick() {
                    }

                })
                .show(activity.supportFragmentManager, "CommonSheetAlertDialog")
        } else if (roomKitBean.isOwner || isMineInfo(data)) { // 房主或者自己
            RoomMicManagerSheetDialog(object : OnItemClickListener<MicManagerBean> {
                override fun onItemClick(data: MicManagerBean, view: View, position: Int, viewType: Long) {
                    when (data.micClickAction) {
                        MicClickAction.Invite -> {
//                            roomAudioViewModel.postInvitationMic()
                        }
                        MicClickAction.Mute -> {}
                        MicClickAction.UnMute -> {}
                        MicClickAction.Block -> {}
                        MicClickAction.UnBlock -> {}
                        MicClickAction.KickOff -> {}
                        MicClickAction.OffStage -> {}
                    }
                }
            }).apply {
                arguments = Bundle().apply {
                    putSerializable(RoomMicManagerSheetDialog.KEY_MIC_INFO, data)
                    putSerializable(RoomMicManagerSheetDialog.KEY_IS_OWNER, roomKitBean.isOwner)
                }
                theme
            }.show(activity.supportFragmentManager, "RoomMicManagerSheetDialog")
        }
    }

    fun onBotMicClick(data: MicInfoBean, isUserBot: Boolean) {
        if (isUserBot) {
            Toast.makeText(activity, "${data.userInfo?.username}", Toast.LENGTH_SHORT).show()
        } else {
            CommonFragmentAlertDialog()
                .titleText(activity.getString(R.string.chatroom_prompt))
                .contentText(activity.getString(R.string.chatroom_open_bot_prompt))
                .leftText(activity.getString(R.string.chatroom_cancel))
                .rightText(activity.getString(R.string.chatroom_confirm))
                .setOnClickListener(object : CommonFragmentAlertDialog.OnClickBottomListener {
                    override fun onConfirmClick() {
                        chatroomViewModel.activeBot(activity, roomKitBean.roomId, true)
                    }
                })
                .show(activity.supportFragmentManager, "botActivatedDialog")
        }
    }
}