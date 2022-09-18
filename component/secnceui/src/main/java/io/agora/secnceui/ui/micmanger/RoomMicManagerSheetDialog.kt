package io.agora.secnceui.ui.micmanger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import io.agora.baseui.adapter.OnItemClickListener
import io.agora.baseui.dialog.BaseSheetDialog
import io.agora.buddy.tool.ViewTools
import io.agora.buddy.tool.dp
import io.agora.secnceui.R
import io.agora.secnceui.annotation.MicStatus
import io.agora.secnceui.annotation.UserRole
import io.agora.secnceui.annotation.AudioVolumeStatus
import io.agora.secnceui.bean.*
import io.agora.secnceui.databinding.DialogChatroomMicManagerBinding
import io.agora.secnceui.ui.mic.RoomMicConstructor

class RoomMicManagerSheetDialog constructor(private val onItemClickListener: OnItemClickListener<MicManagerBean>) :
    BaseSheetDialog<DialogChatroomMicManagerBinding>() {

    companion object {
        const val KEY_MIC_INFO = "mic_info"
        const val KEY_IS_OWNER = "is_owner"
    }

    private var micManagerAdapter: RoomMicManagerAdapter? = null

    private val micManagerList = mutableListOf<MicManagerBean>()

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): DialogChatroomMicManagerBinding {
        return DialogChatroomMicManagerBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.apply {
            val micInfo: MicInfoBean? = get(KEY_MIC_INFO) as? MicInfoBean
            val isOwner: Boolean = getBoolean(KEY_IS_OWNER, false)
            micInfo?.let {
                bindingMicInfo(it)
                if (isOwner) {
                    micManagerList.addAll(RoomMicConstructor.builderOwnerMicMangerList(view.context, it))
                } else {
                    micManagerList.addAll(RoomMicConstructor.builderGuestMicMangerList(view.context, it))
                }
            }
        }
        micManagerAdapter = RoomMicManagerAdapter(micManagerList, object : OnItemClickListener<MicManagerBean> {
            override fun onItemClick(data: MicManagerBean, view: View, position: Int, viewType: Long) {
                Toast.makeText(context, data.name, Toast.LENGTH_SHORT).show()
                onItemClickListener.onItemClick(data, view, position, viewType)
            }
        }, RoomMicManagerViewHolder::class.java)
        binding?.apply {
            setOnApplyWindowInsets(root)

            val itemDecoration =
                MaterialDividerItemDecoration(root.context, MaterialDividerItemDecoration.HORIZONTAL).apply {
                    dividerColor = ResourcesCompat.getColor(root.context.resources, R.color.divider_color_979797, null)
                    dividerThickness = 1.dp.toInt()
                }
            rvChatroomMicManager.addItemDecoration(itemDecoration)
            rvChatroomMicManager.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            rvChatroomMicManager.adapter = micManagerAdapter
        }
    }

    private fun bindingMicInfo(micInfo: MicInfoBean) {
        binding?.mtChatroomMicTag?.isVisible = micInfo.userInfo?.userRole == UserRole.Owner
        binding?.apply {
            // 座位状态
            when (micInfo.micStatus) {
                MicStatus.ForceMute -> {
                    ivMicInnerIcon.isVisible = true
                    ivMicInnerIcon.setImageResource(R.drawable.icon_chatroom_mic_mute)
                    ivMicTag.isVisible = false
                    mtMicUsername.text = micInfo.index.toString()
                }
                MicStatus.Lock -> {
                    ivMicInnerIcon.isVisible = true
                    ivMicInnerIcon.setImageResource(R.drawable.icon_chatroom_mic_close)
                    ivMicTag.isVisible = false
                    mtMicUsername.text = micInfo.index.toString()
                }
                MicStatus.LockForceMute -> {
                    ivMicInnerIcon.isVisible = true
                    ivMicInnerIcon.setImageResource(R.drawable.icon_chatroom_mic_close)
                    ivMicTag.isVisible = true
                    ivMicTag.setImageResource(R.drawable.icon_chatroom_mic_mute_tag)
                    mtMicUsername.text = micInfo.index.toString()
                }
                MicStatus.UserNormal -> {
                    ivMicInnerIcon.isVisible = false
                    mtMicUsername.text = micInfo.userInfo?.username ?: ""
                    ivMicInfo.setImageResource(
                        ViewTools.getDrawableId(ivMicInfo.context, micInfo.userInfo?.userAvatar ?: "")
                    )
                    ivMicTag.isVisible = false
                }
                MicStatus.UserForceMute -> {
                    ivMicInnerIcon.isVisible = false
                    mtMicUsername.text = micInfo.userInfo?.username ?: ""
                    ivMicInfo.setImageResource(
                        ViewTools.getDrawableId(ivMicInfo.context, micInfo.userInfo?.userAvatar ?: "")
                    )
                    ivMicTag.isVisible = true
                    ivMicTag.setImageResource(R.drawable.icon_chatroom_mic_mute_tag)
                }
                else -> { // 空闲
                    ivMicInnerIcon.isVisible = true
                    ivMicInnerIcon.setImageResource(R.drawable.icon_chatroom_mic_add)
                    ivMicTag.isVisible = false
                    mtMicUsername.text = micInfo.index.toString()
                }
            }
            // 用户音量
            when (micInfo.audioVolume) {
//                AudioVolumeStatus.None -> ivMicTag.isVisible = false
                AudioVolumeStatus.Low -> {
                    ivMicTag.isVisible = true
                    ivMicTag.setImageResource(R.drawable.icon_chatroom_mic_open1)
                }
                AudioVolumeStatus.Middle -> {
                    ivMicTag.isVisible = true
                    ivMicTag.setImageResource(R.drawable.icon_chatroom_mic_open2)
                }
                AudioVolumeStatus.High -> {
                    ivMicTag.isVisible = true
                    ivMicTag.setImageResource(R.drawable.icon_chatroom_mic_open3)
                }
                AudioVolumeStatus.Max -> {
                    ivMicTag.isVisible = true
                    ivMicTag.setImageResource(R.drawable.icon_chatroom_mic_open4)
                }
                AudioVolumeStatus.Mute -> {
                    ivMicTag.isVisible = true
                    ivMicTag.setImageResource(R.drawable.icon_chatroom_mic_mute_tag)
                }
                else -> {

                }
            }
        }
    }
}
