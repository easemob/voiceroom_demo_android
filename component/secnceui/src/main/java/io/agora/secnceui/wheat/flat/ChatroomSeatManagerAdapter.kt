package io.agora.secnceui.wheat.flat

import io.agora.baseui.adapter.BaseRecyclerViewAdapter
import io.agora.baseui.adapter.OnItemClickListener
import io.agora.buddy.tool.dp
import io.agora.buddy.tool.getDisplaySize
import io.agora.secnceui.bean.SeatManagerBean
import io.agora.secnceui.databinding.ItemChatroomSeatManagerBinding

class ChatroomSeatManagerAdapter constructor(
    dataList: List<SeatManagerBean>,
    listener: OnItemClickListener<SeatManagerBean>?,
    viewHolderClass: Class<ChatroomSeatManagerViewHolder>
) :
    BaseRecyclerViewAdapter<ItemChatroomSeatManagerBinding, SeatManagerBean, ChatroomSeatManagerViewHolder>(
        dataList, listener, viewHolderClass
    ) {

    override fun onBindViewHolder(holder: ChatroomSeatManagerViewHolder, position: Int) {

        val layoutParams = holder.mBinding.root.layoutParams
        when (dataList.size) {
            1 -> {
                layoutParams.width = getDisplaySize().width
            }
            2 -> {
                layoutParams.width = getDisplaySize().width/2
            }
            else -> {
                layoutParams.width = getDisplaySize().width / 3
            }
        }
        holder.mBinding.root.layoutParams = layoutParams
        super.onBindViewHolder(holder, position)
    }
}