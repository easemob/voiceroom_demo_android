package io.agora.secnceui.wheat.flat

import androidx.core.content.res.ResourcesCompat
import io.agora.baseui.adapter.BaseRecyclerViewAdapter
import io.agora.secnceui.R
import io.agora.secnceui.bean.SeatManagerBean
import io.agora.secnceui.databinding.ItemChatroomSeatManagerBinding


class ChatroomSeatManagerViewHolder(binding: ItemChatroomSeatManagerBinding) :
    BaseRecyclerViewAdapter.BaseViewHolder<ItemChatroomSeatManagerBinding, SeatManagerBean>(binding) {
    override fun binding(data: SeatManagerBean?, selectedIndex: Int) {
        data?.let {
            mBinding.mtChatroomSeatManagerOperate.text = it.name
            if (it.enable) {
                mBinding.mtChatroomSeatManagerOperate.setTextColor(
                    ResourcesCompat.getColor(context.resources, R.color.main_color_156EF3, null)
                )
            } else {
                mBinding.mtChatroomSeatManagerOperate.setTextColor(
                    ResourcesCompat.getColor(context.resources, R.color.dark_grey_color_979CBB, null)
                )
            }
        }
    }
}