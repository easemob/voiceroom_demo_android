package com.easemob.chatroom.ui.adapter

import android.content.res.AssetManager
import android.graphics.Typeface
import androidx.core.view.isVisible
import com.easemob.baseui.adapter.BaseRecyclerViewAdapter
import com.easemob.buddy.tool.ResourcesTools
import com.easemob.chatroom.R
import com.easemob.chatroom.databinding.ItemChatroomContributionRankingBinding
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
            val mgr: AssetManager = context.assets //得到AssetManager
            val tf: Typeface = Typeface.createFromAsset(mgr, "fonts/RobotoNembersVF.ttf") //根据路径得到Typeface
            binding.mtContributionNumber.typeface = tf //设置字体
        }
    }

    private fun setRankNumber() {
        val num = bindingAdapterPosition + 1
        when (bindingAdapterPosition) {
            0 -> {
                binding.ivContributionNumber.isVisible = true
                binding.ivContributionNumber.setImageResource(R.drawable.icon_chatroom_bang1)
                binding.mtContributionNumber.text = num.toString()
            }
            1 -> {
                binding.ivContributionNumber.isVisible = true
                binding.ivContributionNumber.setImageResource(R.drawable.icon_chatroom_bang2)
                binding.mtContributionNumber.text = num.toString()
            }
            2 -> {
                binding.ivContributionNumber.isVisible = true
                binding.ivContributionNumber.setImageResource(R.drawable.icon_chatroom_bang3)
                binding.mtContributionNumber.text = num.toString()
            }
            else -> {
                binding.ivContributionNumber.isVisible = false
                binding.mtContributionNumber.text = num.toString()
            }
        }
    }
}