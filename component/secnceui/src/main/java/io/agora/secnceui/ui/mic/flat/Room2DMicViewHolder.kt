package io.agora.secnceui.ui.mic.flat

import io.agora.baseui.adapter.BaseRecyclerViewAdapter
import io.agora.secnceui.bean.MicInfoBean
import io.agora.secnceui.databinding.ItemChatroom2dMicBinding

class Room2DMicViewHolder(binding: ItemChatroom2dMicBinding) :
    BaseRecyclerViewAdapter.BaseViewHolder<ItemChatroom2dMicBinding, MicInfoBean>(binding) {
    override fun binding(data: MicInfoBean?, selectedIndex: Int) {
        data?.let {
            mBinding.mic2dView.binding(it)
        }
    }
}