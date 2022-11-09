package com.easemob.secnceui.ui.mic.flat

import com.easemob.baseui.adapter.BaseRecyclerViewAdapter
import com.easemob.secnceui.databinding.ItemChatroom2dMicBinding

class Room2DMicViewHolder(binding: ItemChatroom2dMicBinding) :
    BaseRecyclerViewAdapter.BaseViewHolder<ItemChatroom2dMicBinding, com.easemob.secnceui.bean.MicInfoBean>(binding) {
    override fun binding(data: com.easemob.secnceui.bean.MicInfoBean?, selectedIndex: Int) {
        data?.let {
            mBinding.mic2dView.binding(it)
        }
    }
}