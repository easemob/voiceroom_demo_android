package io.agora.chatroom.adapter

import io.agora.baseui.adapter.BaseRecyclerViewAdapter
import io.agora.buddy.tool.ResourcesTools
import io.agora.secnceui.R
import io.agora.secnceui.annotation.MicClickAction
import io.agora.chatroom.databinding.ItemChatroomAudienceListBinding
import tools.bean.VMemberBean
import tools.bean.VRoomUserBean

class RoomAudienceListViewHolder constructor(private val binding: ItemChatroomAudienceListBinding) :
    BaseRecyclerViewAdapter.BaseViewHolder<ItemChatroomAudienceListBinding, VMemberBean>(binding) {

    override fun binding(data: VMemberBean?, selectedIndex: Int) {
        data?.let { audienceInfo ->
            binding.mtAudienceUsername.text = audienceInfo.name
            binding.ivAudienceAvatar.setImageResource(
                ResourcesTools.getDrawableId(binding.ivAudienceAvatar.context, audienceInfo.portrait)
            )
            if (audienceInfo.mic_index == -1) {
                // 不在麦位上
                binding.mtAudienceAction.apply {
                    isClickable = true
                    text = binding.root.context.getString(R.string.chatroom_invite)
                    setTextColor(ResourcesTools.getColor(context.resources, io.agora.baseui.R.color.white))
                    setBackgroundResource(R.drawable.bg_rect_radius20_gradient_blue)
                    setOnClickListener {
                        onItemChildClick(MicClickAction.Invite, it)
                    }
                }
            } else {
                // 在麦位上
                binding.mtAudienceAction.apply {
                    isClickable = true
                    text = binding.root.context.getString(R.string.chatroom_kickoff)
                    setTextColor(ResourcesTools.getColor(context.resources, R.color.main_color_156EF3))
                    setBackgroundResource(0)
                    setOnClickListener {
                        onItemChildClick(MicClickAction.KickOff, it)
                    }
                }

            }
        }
    }
}