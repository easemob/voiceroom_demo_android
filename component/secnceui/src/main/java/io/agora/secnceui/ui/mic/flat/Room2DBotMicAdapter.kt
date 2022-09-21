package io.agora.secnceui.ui.mic.flat

import io.agora.baseui.adapter.BaseRecyclerViewAdapter
import io.agora.baseui.adapter.OnItemChildClickListener
import io.agora.baseui.adapter.OnItemClickListener
import io.agora.buddy.tool.dp
import io.agora.buddy.tool.getDisplaySize
import io.agora.secnceui.annotation.MicStatus
import io.agora.secnceui.bean.BotMicInfoBean
import io.agora.secnceui.databinding.ItemChatroom2dBotMicBinding

class Room2DBotMicAdapter constructor(
    dataList: List<BotMicInfoBean>?,
    listener: OnItemClickListener<BotMicInfoBean>?,
    childListener: OnItemChildClickListener<BotMicInfoBean>?,
    viewHolderClass: Class<Room2DBotMicViewHolder>
) :
    BaseRecyclerViewAdapter<ItemChatroom2dBotMicBinding, BotMicInfoBean, Room2DBotMicViewHolder>(
        dataList, listener, childListener, viewHolderClass
    ) {

    override fun onBindViewHolder(holder: Room2DBotMicViewHolder, position: Int) {
        val layoutParams = holder.mBinding.root.layoutParams
        val size = ((getDisplaySize().width - 28.dp) / 2).toInt()
        layoutParams.width = size
        holder.mBinding.root.layoutParams = layoutParams
        super.onBindViewHolder(holder, position)
    }

    fun activeBot(active: Boolean) {
        dataList.forEach { botMicInfoBean ->
            if (active){
                botMicInfoBean.blueBot.micStatus = MicStatus.BotActivated
                botMicInfoBean.redBot.micStatus = MicStatus.BotActivated
            }else{
                botMicInfoBean.blueBot.micStatus = MicStatus.BotInactive
                botMicInfoBean.redBot.micStatus = MicStatus.BotInactive
            }
        }
        notifyDataSetChanged()
    }
}