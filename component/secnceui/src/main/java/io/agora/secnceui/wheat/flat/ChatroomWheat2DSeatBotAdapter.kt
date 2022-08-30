package io.agora.secnceui.wheat.flat

import io.agora.baseui.adapter.BaseRecyclerViewAdapter
import io.agora.baseui.adapter.OnItemClickListener
import io.agora.buddy.tool.dp
import io.agora.buddy.tool.getDisplaySize
import io.agora.secnceui.bean.BotSeatInfoBean
import io.agora.secnceui.databinding.ItemChatroom2dSeatBotBinding

class ChatroomWheat2DSeatBotAdapter(
    dataList: List<BotSeatInfoBean>?,
    listener: OnItemClickListener<BotSeatInfoBean>?,
    viewHolderClass: Class<ChatroomWheat2DBotViewHolder>
) :
    BaseRecyclerViewAdapter<ItemChatroom2dSeatBotBinding, BotSeatInfoBean, ChatroomWheat2DBotViewHolder>(
        dataList, listener, viewHolderClass
    ) {

    override fun onBindViewHolder(holder: ChatroomWheat2DBotViewHolder, position: Int) {
        val layoutParams = holder.mBinding.root.layoutParams
        val size = ((getDisplaySize().width - 28.dp) / 2).toInt()
        layoutParams.width = size
        holder.mBinding.root.layoutParams = layoutParams
        super.onBindViewHolder(holder, position)
    }
}