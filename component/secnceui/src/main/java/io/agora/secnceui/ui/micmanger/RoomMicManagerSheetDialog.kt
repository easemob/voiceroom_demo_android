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
import io.agora.buddy.tool.ResourcesTools
import io.agora.buddy.tool.dp
import io.agora.config.ConfigConstants
import io.agora.secnceui.R
import io.agora.secnceui.annotation.MicStatus
import io.agora.secnceui.bean.*
import io.agora.secnceui.databinding.DialogChatroomMicManagerBinding
import io.agora.secnceui.ui.mic.RoomMicConstructor

class RoomMicManagerSheetDialog constructor(private val onItemClickListener: OnItemClickListener<MicManagerBean>) :
    BaseSheetDialog<DialogChatroomMicManagerBinding>() {

    companion object {
        const val KEY_MIC_INFO = "mic_info"
        const val KEY_IS_OWNER = "owner_id"
        const val KEY_IS_MYSELF = "is_myself"
    }

    private var micManagerAdapter: RoomMicManagerAdapter? = null

    private val micManagerList = mutableListOf<MicManagerBean>()

    private val micInfo: MicInfoBean? by lazy {
        arguments?.get(KEY_MIC_INFO) as? MicInfoBean
    }
    private val isOwner: Boolean by lazy {
        arguments?.getBoolean(KEY_IS_OWNER, false) ?: false
    }
    private val isMyself: Boolean by lazy {
        arguments?.getBoolean(KEY_IS_MYSELF, false) ?: false
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): DialogChatroomMicManagerBinding {
        return DialogChatroomMicManagerBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.apply {
            micInfo?.let {
                bindingMicInfo(it)
                if (isOwner) {
                    micManagerList.addAll(RoomMicConstructor.builderOwnerMicMangerList(view.context, it, isMyself))
                } else {
                    micManagerList.addAll(RoomMicConstructor.builderGuestMicMangerList(view.context, it))
                }
            }
        }
        micManagerAdapter = RoomMicManagerAdapter(micManagerList, object : OnItemClickListener<MicManagerBean> {
            override fun onItemClick(data: MicManagerBean, view: View, position: Int, viewType: Long) {
                Toast.makeText(context, data.name, Toast.LENGTH_SHORT).show()
                onItemClickListener.onItemClick(data, view, position, viewType)
                dismiss()
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
        binding?.apply {
            // 座位状态
            if (micInfo.userInfo == null) { // 没人
                binding?.mtChatroomMicTag?.isVisible = false
                ivMicInnerIcon.isVisible = true
                mtMicUsername.text = micInfo.index.toString()
                when (micInfo.micStatus) {
                    MicStatus.ForceMute -> {
                        ivMicTag.isVisible = false
                        ivMicInnerIcon.setImageResource(R.drawable.icon_chatroom_mic_mute)
                    }
                    MicStatus.CloseForceMute -> {
                        ivMicInnerIcon.setImageResource(R.drawable.icon_chatroom_mic_close)
                        ivMicTag.isVisible = true
                        ivMicTag.setImageResource(R.drawable.icon_chatroom_mic_mute_tag)
                    }
                    else -> {
                        ivMicTag.isVisible = false
                        ivMicInnerIcon.setImageResource(R.drawable.icon_chatroom_mic_add)
                    }
                }
            } else { // 有人
                binding?.mtChatroomMicTag?.isVisible = micInfo.ownerTag
                ivMicInnerIcon.isVisible = false
                ivMicInfo.setImageResource(
                    ResourcesTools.getDrawableId(ivMicInfo.context, micInfo.userInfo?.userAvatar ?: "")
                )
                mtMicUsername.text = micInfo.userInfo?.username ?: ""
                mtChatroomMicTag.isVisible = micInfo.ownerTag
                when (micInfo.micStatus) {
                    MicStatus.Mute,
                    MicStatus.ForceMute -> {
                        ivMicTag.isVisible = true
                        ivMicTag.setImageResource(R.drawable.icon_chatroom_mic_mute_tag)
                    }
                    else -> {
                        ivMicTag.isVisible = false
                    }
                }
            }
            // 用户音量
            when (micInfo.audioVolumeType) {
                ConfigConstants.VolumeType.Volume_None -> ivMicTag.isVisible = false
                ConfigConstants.VolumeType.Volume_Low -> {
                    ivMicTag.isVisible = true
                    ivMicTag.setImageResource(R.drawable.icon_chatroom_mic_open1)
                }
                ConfigConstants.VolumeType.Volume_Medium -> {
                    ivMicTag.isVisible = true
                    ivMicTag.setImageResource(R.drawable.icon_chatroom_mic_open2)
                }
                ConfigConstants.VolumeType.Volume_High -> {
                    ivMicTag.isVisible = true
                    ivMicTag.setImageResource(R.drawable.icon_chatroom_mic_open3)
                }
                ConfigConstants.VolumeType.Volume_Max -> {
                    ivMicTag.isVisible = true
                    ivMicTag.setImageResource(R.drawable.icon_chatroom_mic_open4)
                }
                else -> {

                }
            }
        }
    }
}
