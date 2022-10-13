package io.agora.secnceui.ui.mic.flat

import io.agora.baseui.adapter.BaseRecyclerViewAdapter
import io.agora.secnceui.bean.BotMicInfoBean
import io.agora.secnceui.databinding.ItemChatroom2dBotMicBinding

class Room2DBotMicViewHolder(binding: ItemChatroom2dBotMicBinding) :
    BaseRecyclerViewAdapter.BaseViewHolder<ItemChatroom2dBotMicBinding, BotMicInfoBean>(binding) {
    override fun binding(data: BotMicInfoBean?, selectedIndex: Int) {
        data?.let {
            mBinding.micBlueBot.binding(it.blueBot)
            mBinding.micBlueRed.binding(it.redBot)
            mBinding.root.setOnClickListener { view ->
                onItemChildClick(it.blueBot, view)
            }
            mBinding.micBlueBot.setOnClickListener { view ->
                onItemChildClick(it.blueBot, view)
            }
            mBinding.micBlueRed.setOnClickListener { view ->
                onItemChildClick(it.redBot, view)
            }
        }
    }
}