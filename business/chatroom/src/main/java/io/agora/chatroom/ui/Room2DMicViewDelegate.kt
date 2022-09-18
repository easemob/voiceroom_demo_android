package io.agora.chatroom.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import io.agora.baseui.adapter.OnItemClickListener
import io.agora.chatroom.general.constructor.RoomInfoConstructor
import io.agora.chatroom.general.repositories.ProfileManager
import io.agora.chatroom.model.RoomMicViewModel
import io.agora.secnceui.R
import io.agora.secnceui.annotation.MicClickAction
import io.agora.secnceui.annotation.MicStatus
import io.agora.secnceui.bean.MicInfoBean
import io.agora.secnceui.bean.MicManagerBean
import io.agora.secnceui.ui.common.CommonSheetAlertDialog
import io.agora.secnceui.ui.micmanger.RoomMicManagerSheetDialog
import io.agora.secnceui.ui.mic.flat.IRoom2DMicView
import tools.bean.VRoomInfoBean

/**
 * @author create by zhangwei03
 */
class Room2DMicViewDelegate constructor(
    private val activity: FragmentActivity,
    private val room2dMicView: IRoom2DMicView
) {

    private val roomAudioViewModel: RoomMicViewModel by lazy {
        ViewModelProvider(activity)[RoomMicViewModel::class.java]
    }

    private var vRoomBean: VRoomInfoBean? = null

    private val roomId by lazy {
        vRoomBean?.room?.room_id ?: ""
    }

    private fun isOwner(): Boolean {
        val currentUid = ProfileManager.getInstance().profile?.uid
        return !currentUid.isNullOrEmpty() && TextUtils.equals(currentUid, vRoomBean?.room?.owner?.uid)
    }

    fun onRoomDetails(vRoomBean: VRoomInfoBean) {
        this.vRoomBean = vRoomBean
        val micInfoList = RoomInfoConstructor.convertMicUiBean(vRoomBean)
        val botInfo = RoomInfoConstructor.convertMicBotUiBean(vRoomBean)
        room2dMicView.updateAdapter(micInfoList, mutableListOf(botInfo))
    }

    fun onUserMicClick(data: MicInfoBean, view: View, position: Int, viewType: Long) {
        // 普通用户,空闲位置
        if (data.micStatus == MicStatus.Idle && !isOwner()) {
            CommonSheetAlertDialog()
                .contentText(activity.getString(R.string.chatroom_request_speak))
                .rightText(activity.getString(R.string.chatroom_confirm))
                .leftText(activity.getString(R.string.chatroom_cancel))
                .setOnClickListener(object : CommonSheetAlertDialog.OnClickBottomListener {
                    override fun onConfirmClick() {
                        roomAudioViewModel.submitMic(activity, roomId, position)
                    }

                    override fun onCancelClick() {
                    }

                })
                .show(activity.supportFragmentManager, "CommonSheetAlertDialog")
        } else {
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
                    putSerializable(RoomMicManagerSheetDialog.KEY_IS_OWNER, isOwner())
                }
                theme
            }.show(activity.supportFragmentManager, "RoomMicManagerSheetDialog")
        }
    }

    fun onBotMicClick(data: MicInfoBean, view: View, position: Int, viewType: Long) {
        Toast.makeText(activity, "${data.userInfo?.username}", Toast.LENGTH_SHORT).show()
    }
}