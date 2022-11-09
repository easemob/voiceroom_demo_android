package com.easemob.chatroom.ui.adapter

import com.easemob.baseui.adapter.BaseRecyclerViewAdapter
import com.easemob.buddy.tool.ResourcesTools
import com.easemob.chatroom.R
import com.easemob.chatroom.databinding.ItemChatroomAudienceListBinding
import tools.bean.VMemberBean

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
                    setTextColor(ResourcesTools.getColor(context.resources, com.easemob.baseui.R.color.white))
                    setBackgroundResource(R.drawable.bg_rect_radius20_gradient_blue)
                    setOnClickListener {
                        onItemChildClick(com.easemob.secnceui.annotation.MicClickAction.Invite, it)
                    }
                }
            } else {
                // 在麦位上
                binding.mtAudienceAction.apply {
                    isClickable = true
                    text = binding.root.context.getString(com.easemob.secnceui.R.string.chatroom_kickoff)
                    setTextColor(ResourcesTools.getColor(context.resources, com.easemob.secnceui.R.color.main_color_156EF3))
                    setBackgroundResource(0)
                    setOnClickListener {
                        onItemChildClick(com.easemob.secnceui.annotation.MicClickAction.KickOff, it)
                    }
                }

            }
        }
    }
}