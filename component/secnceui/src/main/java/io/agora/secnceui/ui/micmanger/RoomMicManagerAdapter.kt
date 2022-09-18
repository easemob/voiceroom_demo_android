package io.agora.secnceui.ui.micmanger

import io.agora.baseui.adapter.BaseRecyclerViewAdapter
import io.agora.baseui.adapter.OnItemClickListener
import io.agora.buddy.tool.getDisplaySize
import io.agora.secnceui.bean.MicManagerBean
import io.agora.secnceui.databinding.ItemChatroomMicManagerBinding

class RoomMicManagerAdapter constructor(
    dataList: List<MicManagerBean>,
    listener: OnItemClickListener<MicManagerBean>?,
    viewHolderClass: Class<RoomMicManagerViewHolder>
) :
    BaseRecyclerViewAdapter<ItemChatroomMicManagerBinding, MicManagerBean, RoomMicManagerViewHolder>(
        dataList, listener, viewHolderClass
    ) {

    override fun onBindViewHolder(holder: RoomMicManagerViewHolder, position: Int) {

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