package com.easemob.secnceui.ui.mic.flat

import com.easemob.baseui.adapter.BaseRecyclerViewAdapter
import com.easemob.baseui.adapter.OnItemClickListener
import com.easemob.buddy.tool.dp
import com.easemob.buddy.tool.getDisplaySize
import com.easemob.secnceui.databinding.ItemChatroom2dMicBinding

class Room2DMicAdapter constructor(
    dataList: List<com.easemob.secnceui.bean.MicInfoBean>?,
    listener: OnItemClickListener<com.easemob.secnceui.bean.MicInfoBean>?,
    viewHolderClass: Class<Room2DMicViewHolder>
) :
    BaseRecyclerViewAdapter<ItemChatroom2dMicBinding, com.easemob.secnceui.bean.MicInfoBean, Room2DMicViewHolder>(
        dataList, listener, viewHolderClass
    ) {

    override fun onBindViewHolder(holder: Room2DMicViewHolder, position: Int) {
        val layoutParams = holder.mBinding.root.layoutParams
        val size = ((getDisplaySize().width - 28.dp) / 4).toInt()
        layoutParams.width = size
        holder.mBinding.root.layoutParams = layoutParams
        super.onBindViewHolder(holder, position)
    }

    fun updateMicStatusByAction(micIndex: Int, @com.easemob.secnceui.annotation.MicClickAction action: Int) {
        if (micIndex >= dataList.size) return
        val micInfo = dataList[micIndex]
        when (action) {
            com.easemob.secnceui.annotation.MicClickAction.ForceMute -> {
                // 禁言（房主操作）
                if (micInfo.micStatus == com.easemob.secnceui.annotation.MicStatus.Lock) {
                    micInfo.micStatus = com.easemob.secnceui.annotation.MicStatus.LockForceMute
                } else {
                    micInfo.micStatus = com.easemob.secnceui.annotation.MicStatus.ForceMute
                }
            }
            // 取消禁言（房主操作）
            com.easemob.secnceui.annotation.MicClickAction.ForceUnMute -> {
                if (micInfo.micStatus == com.easemob.secnceui.annotation.MicStatus.LockForceMute) {
                    micInfo.micStatus = com.easemob.secnceui.annotation.MicStatus.Lock
                } else {
                    if (micInfo.userInfo == null) {
                        micInfo.micStatus = com.easemob.secnceui.annotation.MicStatus.Idle
                    } else {
                        micInfo.micStatus = com.easemob.secnceui.annotation.MicStatus.Normal
                    }
                }
            }
            // 关麦（麦位用户操作包括房主操作自己）
            com.easemob.secnceui.annotation.MicClickAction.Mute -> {
                micInfo.micStatus = com.easemob.secnceui.annotation.MicStatus.Mute
            }
            // 关闭座位（房主操作）
            com.easemob.secnceui.annotation.MicClickAction.UnMute -> {
                if (micInfo.userInfo == null) {
                    micInfo.micStatus = com.easemob.secnceui.annotation.MicStatus.Idle
                } else {
                    micInfo.micStatus = com.easemob.secnceui.annotation.MicStatus.Normal
                }
            }
            // 关闭座位（房主操作）
            com.easemob.secnceui.annotation.MicClickAction.Lock -> {
                if (micInfo.micStatus == com.easemob.secnceui.annotation.MicStatus.ForceMute) {
                    micInfo.micStatus = com.easemob.secnceui.annotation.MicStatus.LockForceMute
                } else {
                    micInfo.micStatus = com.easemob.secnceui.annotation.MicStatus.Lock
                }
            }
            // 打开座位（房主操作）
            com.easemob.secnceui.annotation.MicClickAction.UnLock -> {
                if (micInfo.micStatus == com.easemob.secnceui.annotation.MicStatus.LockForceMute) {
                    micInfo.micStatus = com.easemob.secnceui.annotation.MicStatus.ForceMute
                } else {
                    if (micInfo.userInfo == null) {
                        micInfo.micStatus = com.easemob.secnceui.annotation.MicStatus.Idle
                    } else {
                        micInfo.micStatus = com.easemob.secnceui.annotation.MicStatus.Normal
                    }
                }
            }
            // 强制下麦（房主操作）
            com.easemob.secnceui.annotation.MicClickAction.KickOff -> {
                if (micInfo.micStatus == com.easemob.secnceui.annotation.MicStatus.ForceMute) {
                    micInfo.micStatus = com.easemob.secnceui.annotation.MicStatus.ForceMute
                } else {
                    micInfo.micStatus = com.easemob.secnceui.annotation.MicStatus.Idle
                }
            }
            // 下麦（嘉宾操作）
            com.easemob.secnceui.annotation.MicClickAction.OffStage -> {
                if (micInfo.micStatus == com.easemob.secnceui.annotation.MicStatus.ForceMute) {
                    micInfo.micStatus = com.easemob.secnceui.annotation.MicStatus.ForceMute
                } else {
                    micInfo.micStatus = com.easemob.secnceui.annotation.MicStatus.Idle
                }
            }
            com.easemob.secnceui.annotation.MicClickAction.Invite -> {
                // TODO()
            }
        }
        notifyItemChanged(micIndex)
    }

    fun receiverAttributeMap(newMicMap: Map<Int, com.easemob.secnceui.bean.MicInfoBean>) {
        var needUpdate = false
        // 是否只更新一条
        var onlyOneUpdate = newMicMap.size == 1
        var onlyUpdateItemIndex = -1
        newMicMap.entries.forEach { entry ->
            val index = entry.key
            if (index >= 0 && index < dataList.size) {
                dataList.set(index, entry.value)
                needUpdate = true
                if (onlyOneUpdate) onlyUpdateItemIndex = index
            }
        }
        if (needUpdate) {
            if (onlyUpdateItemIndex >= 0) {
                notifyItemChanged(onlyUpdateItemIndex)
            } else {
                notifyItemRangeChanged(0,dataList.size,true)
            }
        }
    }

    fun updateVolume(index: Int, volume: Int) {
        if (index >= 0 && index < dataList.size) {
            dataList[index].audioVolumeType = volume
            notifyItemChanged(index)
        }
    }
}