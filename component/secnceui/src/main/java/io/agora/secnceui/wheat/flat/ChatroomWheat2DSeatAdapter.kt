package io.agora.secnceui.wheat.flat

import io.agora.baseui.adapter.BaseRecyclerViewAdapter
import io.agora.baseui.adapter.OnItemClickListener
import io.agora.buddy.tool.dp
import io.agora.buddy.tool.getDisplaySize
import io.agora.secnceui.bean.SeatInfoBean
import io.agora.secnceui.databinding.ItemChatroom2dSeatBinding

class ChatroomWheat2DSeatAdapter constructor(
    dataList: List<SeatInfoBean>?,
    listener: OnItemClickListener<SeatInfoBean>?,
    viewHolderClass: Class<ChatroomWheat2DViewHolder>
) :
    BaseRecyclerViewAdapter<ItemChatroom2dSeatBinding, SeatInfoBean, ChatroomWheat2DViewHolder>(
        dataList, listener, viewHolderClass
    ) {

    override fun onBindViewHolder(holder: ChatroomWheat2DViewHolder, position: Int) {
        val layoutParams = holder.mBinding.root.layoutParams
        val size = ((getDisplaySize().width - 28.dp) / 4).toInt()
        layoutParams.width = size
        holder.mBinding.root.layoutParams = layoutParams
        super.onBindViewHolder(holder, position)
    }
}