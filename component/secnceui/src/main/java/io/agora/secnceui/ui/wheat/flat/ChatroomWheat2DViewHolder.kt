package io.agora.secnceui.ui.wheat.flat

import io.agora.baseui.adapter.BaseRecyclerViewAdapter
import io.agora.secnceui.bean.SeatInfoBean
import io.agora.secnceui.databinding.ItemChatroom2dSeatBinding

class ChatroomWheat2DViewHolder(binding: ItemChatroom2dSeatBinding) :
    BaseRecyclerViewAdapter.BaseViewHolder<ItemChatroom2dSeatBinding, SeatInfoBean>(binding) {
    override fun binding(data: SeatInfoBean?, selectedIndex: Int) {
        data?.let {
            mBinding.seat2dView.binding(it)
        }
    }
}