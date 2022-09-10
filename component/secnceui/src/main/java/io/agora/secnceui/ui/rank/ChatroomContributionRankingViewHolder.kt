package io.agora.secnceui.ui.rank

import androidx.core.view.isVisible
import io.agora.baseui.adapter.BaseRecyclerViewAdapter
import io.agora.secnceui.R
import io.agora.secnceui.bean.ContributionBean
import io.agora.secnceui.databinding.ItemChatroomContributionRankingBinding

class ChatroomContributionRankingViewHolder(val binding: ItemChatroomContributionRankingBinding) :
    BaseRecyclerViewAdapter.BaseViewHolder<ItemChatroomContributionRankingBinding, ContributionBean>(binding) {

    override fun binding(data: ContributionBean?, selectedIndex: Int) {
        data?.let {
            setRankNumber(it)
            // TODO: avatar
            binding.mtContributionUsername.text = it.name
            binding.mtContributionValue.text = it.contributionValue.toString()
        }
    }

    private fun setRankNumber(data: ContributionBean) {
        when (data.number) {
            1 -> {
                binding.ivContributionNumber.isVisible = true
                binding.ivContributionNumber.setImageResource(R.drawable.icon_chatroom_bang1)
                binding.mtContributionNumber.isVisible = false
            }
            2 -> {
                binding.ivContributionNumber.isVisible = true
                binding.ivContributionNumber.setImageResource(R.drawable.icon_chatroom_bang2)
                binding.mtContributionNumber.isVisible = false
            }
            3 -> {
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