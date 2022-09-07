package io.agora.secnceui.wheat.flat

import io.agora.baseui.adapter.BaseRecyclerViewAdapter
import io.agora.buddy.tool.ViewTools
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
                    ViewTools.getColor(context.resources, R.color.main_color_156EF3)
                )
            } else {
                mBinding.mtChatroomSeatManagerOperate.setTextColor(
                    ViewTools.getColor(context.resources, R.color.dark_grey_color_979CBB)
                )
            }
        }
    }
}