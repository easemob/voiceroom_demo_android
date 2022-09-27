package io.agora.secnceui.ui.micmanger

import io.agora.baseui.adapter.BaseRecyclerViewAdapter
import io.agora.buddy.tool.ResourcesTools
import io.agora.secnceui.R
import io.agora.secnceui.bean.MicManagerBean
import io.agora.secnceui.databinding.ItemChatroomMicManagerBinding


class RoomMicManagerViewHolder(binding: ItemChatroomMicManagerBinding) :
    BaseRecyclerViewAdapter.BaseViewHolder<ItemChatroomMicManagerBinding, MicManagerBean>(binding) {
    override fun binding(data: MicManagerBean?, selectedIndex: Int) {
        data?.let {
            mBinding.mtMicManagerOperate.text = it.name
            if (it.enable) {
                mBinding.mtMicManagerOperate.setTextColor(
                    ResourcesTools.getColor(context.resources, R.color.main_color_156EF3)
                )
            } else {
                mBinding.mtMicManagerOperate.setTextColor(
                    ResourcesTools.getColor(context.resources, R.color.dark_grey_color_979CBB)
                )
            }
        }
    }
}