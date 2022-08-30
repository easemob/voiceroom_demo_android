package io.agora.secnceui.wheat.flat

import io.agora.baseui.adapter.BaseRecyclerViewAdapter
import io.agora.secnceui.bean.BotSeatInfoBean
import io.agora.secnceui.databinding.ItemChatroom2dSeatBotBinding

class ChatroomWheat2DBotViewHolder(binding: ItemChatroom2dSeatBotBinding) :
    BaseRecyclerViewAdapter.BaseViewHolder<ItemChatroom2dSeatBotBinding, BotSeatInfoBean>(binding) {
    override fun binding(data: BotSeatInfoBean?, selectedIndex: Int) {
        data?.let {
            mBinding.seatBlueBot.binding(it.blueBot)
            mBinding.seatBlueRed.binding(it.redBot)
        }
    }
}