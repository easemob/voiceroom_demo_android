package io.agora.secnceui.ui.rank

import io.agora.baseui.adapter.BaseRecyclerViewAdapter
import io.agora.buddy.tool.ViewTools
import io.agora.secnceui.R
import io.agora.secnceui.annotation.MicClickAction
import io.agora.secnceui.annotation.UserRole
import io.agora.secnceui.bean.AudienceInfoBean
import io.agora.secnceui.databinding.ItemChatroomAudienceListBinding

class RoomAudienceListViewHolder(val binding: ItemChatroomAudienceListBinding) :
    BaseRecyclerViewAdapter.BaseViewHolder<ItemChatroomAudienceListBinding, AudienceInfoBean>(binding) {

    override fun binding(data: AudienceInfoBean?, selectedIndex: Int) {
        data?.let { audienceInfo ->
            // TODO: avatar
            binding.mtAudienceUsername.text = audienceInfo.name

            when (audienceInfo.userRole) {
                UserRole.Owner -> {
                    binding.mtAudienceAction.apply {
                        isClickable = false
                        text = binding.root.context.getString(R.string.chatroom_host)
                        setTextColor(ViewTools.getColor(context.resources, R.color.dark_grey_color_979CBB))
                        setBackgroundResource(0)
                    }

                }
                else -> {
                    setAudienceAction(audienceInfo)
                }
            }
            binding.mtAudienceAction.setOnClickListener {
                onItemChildClick(audienceInfo.micClickAction, it)
            }
        }
    }

    private fun setAudienceAction(audienceInfo: AudienceInfoBean) {
        when (audienceInfo.micClickAction) {
            MicClickAction.Invite -> {
                binding.mtAudienceAction.apply {
                    isClickable = true
                    text = binding.root.context.getString(R.string.chatroom_invite)
                    setTextColor(ViewTools.getColor(context.resources, io.agora.baseui.R.color.white))
                    setBackgroundResource(R.drawable.bg_rect_radius20_gradient_blue)
                }
            }
            MicClickAction.KickOff -> {
                binding.mtAudienceAction.apply {
                    isClickable = true
                    text = binding.root.context.getString(R.string.chatroom_kickoff)
                    setTextColor(ViewTools.getColor(context.resources, R.color.main_color_156EF3))
                    setBackgroundResource(0)
                }
            }
            else -> {
                binding.mtAudienceAction.apply {
                    isClickable = false
                    text = binding.root.context.getString(R.string.chatroom_host)
                    setTextColor(ViewTools.getColor(context.resources, R.color.dark_grey_color_979CBB))
                    setBackgroundResource(0)
                }
            }
        }


    }
}