package io.agora.secnceui.ui.mic.flat

import io.agora.baseui.adapter.BaseRecyclerViewAdapter
import io.agora.baseui.adapter.OnItemChildClickListener
import io.agora.baseui.adapter.OnItemClickListener
import io.agora.buddy.tool.GsonTools
import io.agora.buddy.tool.ThreadManager
import io.agora.buddy.tool.dp
import io.agora.buddy.tool.getDisplaySize
import io.agora.config.ConfigConstants
import io.agora.secnceui.annotation.MicStatus
import io.agora.secnceui.bean.BotMicInfoBean
import io.agora.secnceui.bean.ImAttrBean
import io.agora.secnceui.bean.MicInfoBean
import io.agora.secnceui.databinding.ItemChatroom2dBotMicBinding

class Room2DBotMicAdapter constructor(
    dataList: List<BotMicInfoBean>,
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
        if (active) {
            dataList[0].blueBot.micStatus = MicStatus.BotActivated
            dataList[0].redBot.micStatus = MicStatus.BotActivated
            dataList[0].blueBot.audioVolumeType = ConfigConstants.VolumeType.Volume_None
            dataList[0].redBot.audioVolumeType = ConfigConstants.VolumeType.Volume_None
        } else {
            dataList[0].blueBot.micStatus = MicStatus.BotInactive
            dataList[0].redBot.micStatus = MicStatus.BotInactive
            dataList[0].blueBot.audioVolumeType = ConfigConstants.VolumeType.Volume_None
            dataList[0].redBot.audioVolumeType = ConfigConstants.VolumeType.Volume_None
        }
        notifyItemChanged(0)
    }

    /**更新音量*/
    fun updateVolume(speaker: Int, volume: Int) {
        when (speaker) {
            ConfigConstants.BotSpeaker.BotBlue -> {
                dataList[0].blueBot.audioVolumeType = volume
            }
            ConfigConstants.BotSpeaker.BotRed -> {
                dataList[0].redBot.audioVolumeType = volume
            }
            else -> {
                dataList[0].blueBot.audioVolumeType = volume
                dataList[0].redBot.audioVolumeType = volume
            }
        }
        notifyItemChanged(0)
    }

    fun receiverAttributeMap(newMicMap: Map<Int, MicInfoBean>) {
        if (newMicMap.containsKey(ConfigConstants.MicConstant.KeyIndex6)) {
            val value = newMicMap[ConfigConstants.MicConstant.KeyIndex6]
            ThreadManager.getInstance().runOnMainThread {
                activeBot(value?.micStatus == MicStatus.BotActivated)
            }
        }
    }
}