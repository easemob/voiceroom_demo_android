package io.agora.secnceui.wheat.adapter

import io.agora.baseui.adapter.BaseRecyclerViewAdapter
import io.agora.baseui.adapter.OnItemClickListener
import io.agora.buddy.tool.dp
import io.agora.buddy.tool.getDisplaySize
import io.agora.secnceui.bean.SeatInfoBean
import io.agora.secnceui.databinding.ItemChatroomCommonSeatBinding
import io.agora.secnceui.wheat.adapter.ChatroomWheatViewHolder

class ChatroomWheatSeatAdapter(
    dataList: List<SeatInfoBean>?,
    listener: OnItemClickListener<SeatInfoBean>?,
    viewHolderClass: Class<ChatroomWheatViewHolder>
) :
    BaseRecyclerViewAdapter<ItemChatroomCommonSeatBinding, SeatInfoBean, ChatroomWheatViewHolder>(
        dataList, listener, viewHolderClass
    ) {

    override fun onBindViewHolder(holder: ChatroomWheatViewHolder, position: Int) {
        val layoutParams = holder.mBinding.root.layoutParams
        val size = ((getDisplaySize().width - 28.dp) / 4).toInt()
        layoutParams.width = size
        layoutParams.height = size + 24.dp.toInt()
        holder.mBinding.root.layoutParams = layoutParams
        super.onBindViewHolder(holder, position)
    }
}