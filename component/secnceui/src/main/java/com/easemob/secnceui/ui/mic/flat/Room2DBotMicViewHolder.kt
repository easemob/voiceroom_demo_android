package com.easemob.secnceui.ui.mic.flat

import com.easemob.baseui.adapter.BaseRecyclerViewAdapter
import com.easemob.secnceui.bean.BotMicInfoBean
import com.easemob.secnceui.databinding.ItemChatroom2dBotMicBinding

class Room2DBotMicViewHolder(binding: ItemChatroom2dBotMicBinding) :
    BaseRecyclerViewAdapter.BaseViewHolder<ItemChatroom2dBotMicBinding, BotMicInfoBean>(binding) {
    override fun binding(data: BotMicInfoBean?, selectedIndex: Int) {
        data?.let {
            mBinding.micBlueBot.binding(it.blueBot)
            mBinding.micBlueRed.binding(it.redBot)
            mBinding.micBlueBot.setOnClickListener { view ->
                onItemChildClick(it.blueBot, view)
            }
            mBinding.micBlueRed.setOnClickListener { view ->
                onItemChildClick(it.redBot, view)
            }
        }
    }
}