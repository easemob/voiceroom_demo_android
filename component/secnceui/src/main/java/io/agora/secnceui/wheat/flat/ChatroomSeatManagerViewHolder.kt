package io.agora.secnceui.wheat.flat

import io.agora.baseui.adapter.BaseRecyclerViewAdapter
import io.agora.secnceui.bean.SeatManagerBean
import io.agora.secnceui.databinding.ItemChatroomSeatManagerBinding


class ChatroomSeatManagerViewHolder(binding: ItemChatroomSeatManagerBinding) :
    BaseRecyclerViewAdapter.BaseViewHolder<ItemChatroomSeatManagerBinding, SeatManagerBean>(binding) {
    override fun binding(data: SeatManagerBean?, selectedIndex: Int) {
        data?.let {
            mBinding.mtChatroomSeatManagerOperate.text = it.name
        }
    }
}