package com.easemob.secnceui.ui.micmanger

import com.easemob.baseui.adapter.BaseRecyclerViewAdapter
import com.easemob.buddy.tool.ResourcesTools
import com.easemob.secnceui.R
import com.easemob.secnceui.bean.MicManagerBean
import com.easemob.secnceui.databinding.ItemChatroomMicManagerBinding


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