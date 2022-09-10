package io.agora.secnceui.ui.rank

import io.agora.baseui.adapter.BaseRecyclerViewAdapter
import io.agora.buddy.tool.ViewTools
import io.agora.secnceui.R
import io.agora.secnceui.annotation.WheatSeatAction
import io.agora.secnceui.annotation.WheatUserRole
import io.agora.secnceui.bean.AudienceInfoBean
import io.agora.secnceui.databinding.ItemChatroomAudienceListBinding

class ChatroomAudienceListViewHolder(val binding: ItemChatroomAudienceListBinding) :
    BaseRecyclerViewAdapter.BaseViewHolder<ItemChatroomAudienceListBinding, AudienceInfoBean>(binding) {

    override fun binding(data: AudienceInfoBean?, selectedIndex: Int) {
        data?.let { audienceInfo ->
            // TODO: avatar
            binding.mtAudienceUsername.text = audienceInfo.name

            when (audienceInfo.userRole) {
                WheatUserRole.Owner -> {
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
                onItemChildClick(audienceInfo.seatActionType, it)
            }
        }
    }

    private fun setAudienceAction(audienceInfo: AudienceInfoBean) {
        when (audienceInfo.seatActionType) {
            WheatSeatAction.Invite -> {
                binding.mtAudienceAction.apply {
                    isClickable = true
                    text = binding.root.context.getString(R.string.chatroom_invite)
                    setTextColor(ViewTools.getColor(context.resources, io.agora.baseui.R.color.white))
                    setBackgroundResource(R.drawable.bg_rect_radius20_gradient_blue)
                }
            }
            WheatSeatAction.KickOff -> {
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