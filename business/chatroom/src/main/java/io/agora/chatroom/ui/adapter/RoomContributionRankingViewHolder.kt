package io.agora.chatroom.ui.adapter

import androidx.core.view.isVisible
import io.agora.baseui.adapter.BaseRecyclerViewAdapter
import io.agora.buddy.tool.ResourcesTools
import io.agora.secnceui.R
import io.agora.chatroom.databinding.ItemChatroomContributionRankingBinding
import tools.bean.VRankingMemberBean

class RoomContributionRankingViewHolder(val binding: ItemChatroomContributionRankingBinding) :
    BaseRecyclerViewAdapter.BaseViewHolder<ItemChatroomContributionRankingBinding, VRankingMemberBean>(binding) {

    override fun binding(data: VRankingMemberBean?, selectedIndex: Int) {
        data?.let {
            setRankNumber()
            binding.ivAudienceAvatar.setImageResource(
                ResourcesTools.getDrawableId(binding.ivAudienceAvatar.context, it.portrait)
            )
            binding.mtContributionUsername.text = it.name
            binding.mtContributionValue.text = it.amount.toString()
        }
    }

    private fun setRankNumber() {
        when (bindingAdapterPosition) {
            0 -> {
                binding.ivContributionNumber.isVisible = true
                binding.ivContributionNumber.setImageResource(R.drawable.icon_chatroom_bang1)
                binding.mtContributionNumber.isVisible = false
            }
            1 -> {
                binding.ivContributionNumber.isVisible = true
                binding.ivContributionNumber.setImageResource(R.drawable.icon_chatroom_bang2)
                binding.mtContributionNumber.isVisible = false
            }
            2 -> {
                binding.ivContributionNumber.isVisible = true
                binding.ivContributionNumber.setImageResource(R.drawable.icon_chatroom_bang3)
                binding.mtContributionNumber.isVisible = false
            }
            else -> {
                binding.ivContributionNumber.isVisible = false
                binding.mtContributionNumber.isVisible = true
            }
        }
    }
}