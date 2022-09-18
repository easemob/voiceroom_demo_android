package io.agora.secnceui.ui.mic.flat

import io.agora.baseui.adapter.BaseRecyclerViewAdapter
import io.agora.baseui.adapter.OnItemClickListener
import io.agora.buddy.tool.dp
import io.agora.buddy.tool.getDisplaySize
import io.agora.secnceui.bean.MicInfoBean
import io.agora.secnceui.databinding.ItemChatroom2dMicBinding

class Room2DMicAdapter constructor(
    dataList: List<MicInfoBean>?,
    listener: OnItemClickListener<MicInfoBean>?,
    viewHolderClass: Class<Room2DMicViewHolder>
) :
    BaseRecyclerViewAdapter<ItemChatroom2dMicBinding, MicInfoBean, Room2DMicViewHolder>(
        dataList, listener, viewHolderClass
    ) {

    override fun onBindViewHolder(holder: Room2DMicViewHolder, position: Int) {
        val layoutParams = holder.mBinding.root.layoutParams
        val size = ((getDisplaySize().width - 28.dp) / 4).toInt()
        layoutParams.width = size
        holder.mBinding.root.layoutParams = layoutParams
        super.onBindViewHolder(holder, position)
    }
}