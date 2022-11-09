package com.easemob.secnceui.ui.micmanger

import com.easemob.baseui.adapter.BaseRecyclerViewAdapter
import com.easemob.baseui.adapter.OnItemClickListener
import com.easemob.buddy.tool.getDisplaySize
import com.easemob.secnceui.bean.MicManagerBean
import com.easemob.secnceui.databinding.ItemChatroomMicManagerBinding

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